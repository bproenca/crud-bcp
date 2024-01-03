desc users;
select * from users;
select count(*) from users;

select * from users order by age desc, first_name asc;
select count(*), gender from users group by gender;

select * from users order by id desc;

select id, info_ci, TO_CHAR(ins_date, 'DD/MM/YYYY HH24:MI:SS') ins_date from ATABLE order by id desc;

