package com.zyc.baselibs.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;

public class MybatisSqlProviderTest {
	static MybatisSqlProvider provider  = null;
	
	@Before
	public void before() {
		provider = new MybatisSqlProvider();
	}
	
	@Test
	public void insertTest() {
		_Test test = new _Test();
		String sql = null;
		try {
			sql = provider.insert(test);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
		}
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");

		sql = provider.insert(test);
		assertEquals(sql, "insert into tests(username,description,id,createdat,version) values(#{name},#{description},#{id},#{createdat},#{version})");
	}
	
	@Test
	public void deleteTest() {
		_Test test = new _Test();
		String sql = null;
		try {
			sql = provider.delete(test);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
		}
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");

		sql = provider.delete(test);
		assertEquals(sql, "delete from tests where 1=1  and username=#{name} and description=#{description} and id=#{id} and createdat=#{createdat} and version=#{version}");
	}
	
	@Test
	public void updateTest() {
		_Test test = new _Test();
		String sql = provider.update(test);
		assertEquals(sql, " update tests set username=#{name},description=#{description},createdat=#{createdat},version=#{version} where id=#{id} and version<#{version}");
		_Test2 test2 = new _Test2();
		try {
			sql = provider.update(test2);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
			assertEquals(e.getMessage().contains("Cannot find the"), true);
		}

		_Test3 test3 = new _Test3();
		try {
			sql = provider.update(test3);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
			assertEquals(e.getMessage().contains("The primary key"), true);
		}
		
		_Test4 test4 = new _Test4();
		sql = provider.update(test4);
		assertEquals(sql, " update test4s set name=#{name} where id=#{id}");
	}
	
}

@DatabaseTable(name = "test4s")
class _Test4 {
	@DatabaseColumn(pk = true)
	private String id;
	
	private String name;

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

@DatabaseTable(name = "test3s")
class _Test3 {
	private String id;
	
	private String name;

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

@DatabaseTable(name = "test2s")
class _Test2 {
	@DatabaseColumn(pk = true)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

@DatabaseTable(name = "tests")
class _Test extends Base {
	@SuppressWarnings("unused")
	private static String a = "a";
	
	@SuppressWarnings("unused")
	private final String b = "b";
	
	@DatabaseColumn(name = "username")
	private String name;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

class Base {
	
	@DatabaseColumn(pk = true)
	private String id;
	
	@DatabaseColumn
	private Date createdat;
	
	@DatabaseColumn(version = true)
	private String version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}