insert into role_has_function (role_id,function_id) values(2,1);
insert into role_has_function (role_id,function_id) values(2,2);
insert into role_has_function (role_id,function_id) values(2,3);
insert into role_has_function (role_id,function_id) values(2,4);
insert into role_has_function (role_id,function_id) values(2,5);
insert into role_has_function (role_id,function_id) values(2,6);
insert into role_has_function (role_id,function_id) values(2,7);
insert into role_has_function (role_id,function_id) values(2,8);
insert into role_has_function (role_id,function_id) values(2,9);
insert into role_has_function (role_id,function_id) values(2,10);
insert into role_has_function (role_id,function_id) values(2,11);
insert into role_has_function (role_id,function_id) values(2,12);
insert into role_has_function (role_id,function_id) values(2,13);
insert into role_has_function (role_id,function_id) values(2,14);
insert into role_has_function (role_id,function_id) values(2,15);


select * from contract where id = (select cid from contract_state where type='"+state+"')

//选取一个合同最大ID ＝ 0

select *from contract,contract_state where contract.id=contract_state.cid AND max(contract_state.type)='0' group by contract.id;

select *from contract_state where max(contract_state.type)='0' group by (contract_state.time);


;


select contract.* from contract_state,contract where cid where type>(select max(type) from contract_state where =0 group by cid);


//找到最大状态等于0的cid

select cid from type>=(select max(type) from contract_state where)


得到最大zzz


select id,max(type) from contract_state group by cid;