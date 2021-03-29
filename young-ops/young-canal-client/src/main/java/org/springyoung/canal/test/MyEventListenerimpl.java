package org.springyoung.canal.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springyoung.canal.client.abstracts.option.content.DeleteOption;
import org.springyoung.canal.client.abstracts.option.content.InsertOption;
import org.springyoung.canal.client.abstracts.option.content.UpdateOption;
import org.springyoung.canal.client.core.DealCanalEventListener;

/**
 * 实现接口方式测试
 *
 */
/*@Component
public class MyEventListenerimpl extends DealCanalEventListener {
	
	@Autowired
	public MyEventListenerimpl(@Qualifier("realInsertOptoin") InsertOption insertOption, @Qualifier("realDeleteOption") DeleteOption deleteOption, @Qualifier("realUpdateOption") UpdateOption updateOption) {
		super(insertOption, deleteOption, updateOption);
	}
	
}*/
