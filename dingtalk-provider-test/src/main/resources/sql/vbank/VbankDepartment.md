listUcDepartments
===
SELECT
	id,
	NAME,
	dingding_deptid,
	department_code 
FROM
	uc_departments 
WHERE
	`status` = 1