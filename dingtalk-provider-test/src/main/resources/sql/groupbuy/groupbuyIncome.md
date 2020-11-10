getGroupOrderIncome
===
*获取团购收入
SELECT
	sum(o.order_price) AS price,
	COUNT(o.order_price) AS count 
FROM
	gp_group_orders o
	INNER JOIN gp_departments d ON d.id = o.sale_department_id 
WHERE
	o.status = 1 
	AND o.pay_state = 2 
	AND d.oa_department_id = #oaDeptId#
	AND DATE(o.pay_time) BETWEEN #queryStartTime# and #queryEndTime#

getGrdOrderIncome
===
*获取近卫军(地推)收入
SELECT 
	sum(o.price) AS grd_price,
	COUNT(o.price) AS grd_count 
FROM
	grd_orders o
	INNER JOIN gp_departments d ON d.id = o.belong_department_id 
WHERE
	o.status = 1 
	AND o.pay_state = 2 
	AND d.oa_department_id = #oaDeptId# 
	AND DATE(o.pay_time) BETWEEN #queryStartTime# and #queryEndTime#