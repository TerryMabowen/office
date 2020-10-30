listDtDepartments
===
SELECT
	id,
	NAME,
	last_sync_time,
	oa_department_code,
	oa_department_name 
FROM
	dt_departments 
WHERE
	corp_id = 1

updateDtDepartmentCode
===
UPDATE dt_departments 
SET oa_department_code = #oaDepartmentCode# 
WHERE
	id = #id#