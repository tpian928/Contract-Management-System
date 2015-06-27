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