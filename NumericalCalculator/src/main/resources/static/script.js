const form = document.getElementById("calculatorForm");
const methodInput = document.getElementById("method");
const intervalFields = document.getElementById("intervalFields");
const initialFields = document.getElementById("initialFields");
const x1Field = document.getElementById("x1Field");
const calculateButton = document.getElementById("calculateButton");
const emptyState = document.getElementById("emptyState");
const resultContent = document.getElementById("resultContent");
const errorMessage = document.getElementById("errorMessage");
const tableSection = document.getElementById("tableSection");
const iterationBody = document.getElementById("iterationBody");
const historyList = document.getElementById("historyList");

const methodNames = {
    bisection: "Bisection Method",
    falseposition: "False Position Method",
    newtonraphson: "Newton-Raphson Method",
    secant: "Secant Method"
};

function updateFields() {
    const method = methodInput.value;
    const isInterval = method === "bisection" || method === "falseposition";
    intervalFields.classList.toggle("hidden", !isInterval);
    initialFields.classList.toggle("hidden", isInterval);
    x1Field.classList.toggle("hidden", method !== "secant");
    document.getElementById("xl").required = isInterval;
    document.getElementById("xu").required = isInterval;
    document.getElementById("x0").required = !isInterval;
    document.getElementById("x1").required = method === "secant";
}

function getNumber(id) {
    const input = document.getElementById(id);
    if (input.value.trim() === "") throw new Error("กรุณากรอกตัวเลขให้ครบ");
    const value = Number(input.value);
    if (!Number.isFinite(value)) throw new Error("กรุณากรอกตัวเลขให้ถูกต้อง");
    return value;
}

function formatNumber(value) {
    if (!Number.isFinite(value)) return "—";
    return Number(value).toPrecision(10).replace(/\.?0+$/, "");
}

function showError(message) {
    errorMessage.textContent = message;
    errorMessage.classList.remove("hidden");
}

function showResult(data) {
    emptyState.classList.add("hidden");
    resultContent.classList.remove("hidden");
    errorMessage.classList.add("hidden");
    tableSection.classList.remove("hidden");
    document.getElementById("rootValue").textContent = formatNumber(data.root);
    document.getElementById("fxValue").textContent = formatNumber(data.fx);
    document.getElementById("iterationCount").textContent = data.iterations.length;
    document.getElementById("resultMessage").textContent = data.message;
    const status = document.getElementById("status");
    status.textContent = "COMPLETED";
    status.classList.add("success");
    iterationBody.replaceChildren();

    data.iterations.forEach(item => {
        const row = document.createElement("tr");
        [item.iteration, formatNumber(item.x), formatNumber(item.fx), formatNumber(item.error)]
            .forEach(value => {
                const cell = document.createElement("td");
                cell.textContent = value;
                row.appendChild(cell);
            });
        iterationBody.appendChild(row);
    });
}

function renderHistory(items) {
    historyList.replaceChildren();
    if (!items || items.length === 0) {
        const message = document.createElement("p");
        message.className = "muted";
        message.textContent = "ยังไม่มีประวัติการคำนวณ";
        historyList.appendChild(message);
        return;
    }

    items.forEach(item => {
        const card = document.createElement("div");
        card.className = "history-item";
        const details = document.createElement("div");
        const method = document.createElement("div");
        method.className = "history-method";
        method.textContent = methodNames[item.method] || item.method;
        const equation = document.createElement("p");
        equation.textContent = "f(x) = " + item.functionText;
        const date = document.createElement("p");
        date.textContent = item.createdAt ? new Date(item.createdAt).toLocaleString("th-TH") : "";
        details.append(method, equation, date);
        const root = document.createElement("div");
        root.className = "history-root";
        root.textContent = formatNumber(item.root);
        card.append(details, root);
        historyList.appendChild(card);
    });
}

async function loadHistory() {
    try {
        const response = await fetch("/api/history");
        if (!response.ok) throw new Error("โหลดประวัติไม่สำเร็จ");
        renderHistory(await response.json());
    } catch {
        historyList.textContent = "ไม่สามารถโหลดประวัติจากฐานข้อมูลได้";
    }
}

form.addEventListener("submit", async event => {
    event.preventDefault();
    errorMessage.classList.add("hidden");
    calculateButton.disabled = true;
    calculateButton.textContent = "Calculating...";

    try {
        const method = methodInput.value;
        const request = {
            method,
            function: document.getElementById("function").value.trim(),
            tolerance: getNumber("tolerance"),
            maxIterations: getNumber("maxIterations")
        };

        if (!request.function) throw new Error("กรุณากรอกสมการ");
        if (request.tolerance <= 0) throw new Error("Tolerance ต้องมากกว่า 0");
        if (!Number.isInteger(request.maxIterations) ||
            request.maxIterations < 1 || request.maxIterations > 10000) {
            throw new Error("Max Iterations ต้องเป็นจำนวนเต็มตั้งแต่ 1 ถึง 10000");
        }

        if (method === "bisection" || method === "falseposition") {
            request.xl = getNumber("xl");
            request.xu = getNumber("xu");
        } else {
            request.x0 = getNumber("x0");
            if (method === "secant") request.x1 = getNumber("x1");
        }

        const response = await fetch("/api/calculate", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(request)
        });
        const data = await response.json();
        if (!response.ok) throw new Error(data.error || "คำนวณไม่สำเร็จ");

        showResult(data);
        await loadHistory();
    } catch (error) {
        showError(error.message || "ไม่สามารถเชื่อมต่อเซิร์ฟเวอร์ได้");
        const status = document.getElementById("status");
        status.textContent = "ERROR";
        status.classList.remove("success");
    } finally {
        calculateButton.disabled = false;
        calculateButton.innerHTML = 'Calculate Root <span>→</span>';
    }
});

document.getElementById("clearTable").addEventListener("click", () => {
    tableSection.classList.add("hidden");
});

document.getElementById("clearAllHistory").addEventListener("click", async () => {
    if (!confirm("ต้องการลบประวัติการคำนวณทั้งหมดจากฐานข้อมูลหรือไม่?")) return;
    try {
        const response = await fetch("/api/history", {method: "DELETE"});
        if (!response.ok) throw new Error();
        await loadHistory();
    } catch {
        showError("ไม่สามารถลบประวัติได้");
    }
});

methodInput.addEventListener("change", updateFields);
updateFields();
loadHistory();
