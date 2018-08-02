package com.zyc.baselibs.annotation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DatabaseUtilsTest {
	
	@Test
	public void getTableNameTest() {
		assertEquals(DatabaseUtils.getTableName(A.class), "");
		assertEquals(DatabaseUtils.getTableName(B.class), "b");
		assertEquals(DatabaseUtils.getTableName(DatabaseUtils.class), null);
	}

	@Test
	public void getColumnTest() throws Exception {
		assertEquals(DatabaseUtils.getColumn(A.class.getDeclaredField("id")) != null, true);
		assertEquals(DatabaseUtils.getColumn(A.class.getDeclaredField("nick")) == null, true);
	}
	
	@Test
	public void getColumnNameTest() throws Exception {
		assertEquals(DatabaseUtils.getColumnName(A.class.getDeclaredField("id")), "id");
		assertEquals(DatabaseUtils.getColumnName(A.class.getDeclaredField("name")), "username");
		assertEquals(DatabaseUtils.getColumnName(A.class.getDeclaredField("nick")), null);
	}
	

	@DatabaseTable
	class A {
		@DatabaseColumn
		private String id;

		@DatabaseColumn(name = "username")
		private String name;
		
		private String nick;

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

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}
	}
	
	@DatabaseTable(name = "b")
	class B {
		
	}
}