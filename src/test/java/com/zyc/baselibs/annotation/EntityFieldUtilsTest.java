package com.zyc.baselibs.annotation;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.Test;

import com.zyc.baselibs.commons.Counter;
import com.zyc.baselibs.commons.Visitor;
import com.zyc.baselibs.ex.IllegalEditedException;
import com.zyc.baselibs.ex.IllegalValueException;

public class EntityFieldUtilsTest {
	
	public void eachEntityFieldTest() {

		EF ef = new EF();

		final Counter counter = new Counter();
		
		EntityFieldUtils.eachEntityField(ef, new Visitor<Field, Boolean>() {
			public Boolean visit(Field o) {
				counter.beforePlus();
				return true;
			};
		});

		assertEquals(counter.get(), 5);

		final Counter counter2 = new Counter();
		
		EntityFieldUtils.eachEntityField(ef, new Visitor<Field, Boolean>() {
			public Boolean visit(Field o) {
				counter2.afterPlus();
				return true;
			};
		}, true);

		assertEquals(counter2.get(), 1);
	}
	
	@Test
	public void uneditableFieldsTest() {
		String[] fields = null;
		
		EF ef = new EF();
		
		try {
			fields = EntityFieldUtils.uneditableFields(ef);
		} catch (Exception e) {
			assertEquals(e instanceof IllegalEditedException || e instanceof RuntimeException, true);
		}
		
		boolean flag = null != fields && fields.length == 3;
		assertEquals(flag, true);
		if(flag) {
			String str = "id;name;createdat;";
			for (String field : fields) {
				assertEquals(str.contains(field + ";"), true);
				str = str.replace(field + ";", "");
			}
			assertEquals(str, "");
		}
	}
	
	@Test
	public void verifyRequiredTest() {
		boolean flag = false;
		
		EF ef = new EF();
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		ef.setId("1");
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		ef.setName("Michael");
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		ef.setNick("Michael");
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);
		
		ef.setCreatedat(new Date());
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, false);

		ef.setStatus("enabled");
		
		try {
			EntityFieldUtils.verifyRequired(ef);
			flag = true;
		} catch (Exception e) {
			assertEquals(e instanceof IllegalValueException || e instanceof RuntimeException, true);
		}
		
		assertEquals(flag, true);
	}
	
	class CD {
		
		@EntityField(required = true) 
		private String status;
		@EntityField(required = true, uneditable = true) 
		private Date createdat;
		
		public CD(String status, Date createdat) {
			this.status = status;
			this.createdat = createdat;
		}
		
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getCreatedat() {
			return createdat;
		}

		public void setCreatedat(Date createdat) {
			this.createdat = createdat;
		}

		public CD() {
			
		}
	}

	class EF extends CD {
		@EntityField(required = true, uneditable = true) 
		private String id;

		@EntityField(required = true, uneditable = true)
		private String name;

		@EntityField(required = true)
		private String nick;
		
		private String description;
		
		public EF() {}
		
		public EF(String id, String name, String nick, String description, String status, Date createdat) {
			super(status, createdat);
			this.id = id;
			this.name = name;
			this.nick = nick;
			this.description = description;
		}
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getNick() {
			return nick;
		}
		public void setNick(String nick) {
			this.nick = nick;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
