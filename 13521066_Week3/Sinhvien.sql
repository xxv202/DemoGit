create database Sinhvien
go
use Sinhvien
go

CREATE TABLE Student
(
	MSSV char(10) not null primary key,
	Hoten nvarchar(30) not null,
	NgaySinh datetime not null,
	DiemToan float not null,
	DiemLy float not null,
	DiemHoa float not null,
	DTB float not null
)
set dateformat dmy;
insert into Student values ('09520002','Thạch Sanh','01/01/1995',6.0,4.5,7.0,)
-------------Tạo thủ tục insert sinh viên---------------
go
Create procedure sp_insert_student
	@mssv char(10),
	@hoten nvarchar(30),
	@ngaysinh datetime,
	@Diemtoan float,
	@Diemly float,
	@Diemhoa float
AS
BEGIN
	IF(EXISTS(SELECT * FROM Student sv WHERE sv.MSSV = @mssv))
	BEGIN
		PRINT N'Mã số sinh viên' +@mssv+ N'đã tồn tại'
		RETURN -1
	END
	INSERT INTO Student VALUES (@mssv,@hoten,@ngaysinh,@Diemtoan,@Diemly,@Diemhoa,0.0)
	RETURN 0
END

DECLARE @KQ INT
EXEC @KQ = sp_insert_student '09520001','Thạch Sanh','01/01/1995',6.0,4.5,7.0
-----------Tạo thủ tục update sinh viên --------------
go
Create procedure sp_update_student
	@mssv char(10),
	@hoten nvarchar(30),
	@ngaysinh datetime,
	@Diemtoan float,
	@Diemly float,
	@Diemhoa float
AS
BEGIN
	UPDATE Student SET Hoten = @hoten , NgaySinh = @ngaySinh , DiemToan = @Diemtoan , DiemLy = @Diemly , DiemHoa = @Diemhoa 
				WHERE (MSSV = @mssv)
END

EXEC sp_update_student '09520004','Thị Nở','12/12/1995',7.0,8.0,8.0
----------Tạo thủ tục delete sinh viên ------------------------
go
Create procedure sp_delete_student
	@mssv char(10)
AS
BEGIN
	DELETE FROM Student WHERE (MSSV = @mssv)
END

EXEC sp_delete_student '09520004'

------------TRIGGER----------------------------------------------------------------
go
CREATE TRIGGER Add_SV
On Student
For Insert
As
BEGIN
	DECLARE @Toan float, @Ly float, @Hoa float, @Dtb float
	DECLARE @mssv char(10)
	SET @Toan = (SELECT DiemToan FROM inserted)
	SET @Ly = (SELECT DiemLy FROM inserted)
	SET @Hoa = (SELECT DiemHoa FROM inserted)
	SET @Dtb = ((@Toan + @Ly + @Hoa) / 3)
	SET @mssv = (SELECT MSSV FROM inserted)

	BEGIN
		UPDATE Student SET DTB = @Dtb WHERE (MSSV = @mssv)
	END

END

-----UPDATE------
go
CREATE TRIGGER Update_SV
ON Student
FOR Update
AS
BEGIN
	If (update(DiemToan) or update(DiemLy) or update(DiemHoa))
	BEGIN
		DECLARE @Toan float, @Ly float, @Hoa float, @Dtb float
		DECLARE @mssv char(10)
		SET @Toan = (SELECT DiemToan FROM inserted)
		SET @Ly = (SELECT DiemLy FROM inserted)
		SET @Hoa = (SELECT DiemHoa FROM inserted)
		SET @Dtb = ((@Toan + @Ly + @Hoa) / 3)
		SET @mssv = (SELECT MSSV FROM inserted)

		BEGIN
			UPDATE Student SET DTB = @Dtb WHERE (MSSV = @mssv)
		END

	END
END
