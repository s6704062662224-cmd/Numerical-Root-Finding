# Numera - Numerical Methods Calculator

เว็บคำนวณ Numerical Methods ด้วย Java, Spring Boot, OOP และฐานข้อมูล H2

## สิ่งที่ต้องติดตั้ง
- JDK 17 ขึ้นไป
- IntelliJ IDEA (แนะนำ) หรือ VS Code ที่รองรับ Java
- อินเทอร์เน็ตครั้งแรกเพื่อให้ Maven ดาวน์โหลด dependencies

## วิธีเปิดโปรเจกต์
1. แตกไฟล์ `NumericalCalculator.zip`
2. เปิด IntelliJ IDEA แล้วเลือก **Open**
3. เลือกโฟลเดอร์ `NumericalCalculator` ที่มีไฟล์ `pom.xml`
4. รอ Maven โหลด dependencies
5. เปิด `src/main/java/com/example/numerical/NumericalCalculatorApplication.java`
6. กดปุ่ม Run สีเขียว
7. เปิดเบราว์เซอร์ที่ http://localhost:8080

หรือใช้ Terminal ในโฟลเดอร์โปรเจกต์:
- Windows: `mvnw.cmd spring-boot:run` (ถ้ามี Maven Wrapper) หรือ `mvn spring-boot:run`
- macOS/Linux: `./mvnw spring-boot:run` (ถ้ามี Maven Wrapper) หรือ `mvn spring-boot:run`

## ฐานข้อมูล
ใช้ H2 Database แบบไฟล์ อยู่ที่ `./data/numera.mv.db` และจะสร้างให้อัตโนมัติเมื่อเปิดโปรแกรม
ประวัติการคำนวณจะอยู่ในฐานข้อมูล ไม่ใช่แค่ localStorage ของเบราว์เซอร์
สามารถเปิดหน้าจัดการฐานข้อมูลได้ที่ http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:file:./data/numera`
- User Name: `sa`
- Password: เว้นว่าง

## ตัวอย่าง
- Function: `x^3-x-2`
- Bisection: XL = `1`, XU = `2`
- Tolerance: `0.000001`
- Max Iterations: `100`

รากคำตอบควรอยู่ใกล้ 1.5213797

## วิธีคำนวณที่รองรับ
- Bisection
- False Position
- Newton-Raphson
- Secant

หมายเหตุ: เป็นโปรเจกต์ตัวอย่างเพื่อการศึกษา ควรทดลองทุกวิธีและตรวจสอบผลก่อนนำเสนอ
