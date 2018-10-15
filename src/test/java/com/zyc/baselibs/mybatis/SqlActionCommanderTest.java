package com.zyc.baselibs.mybatis;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.zyc.baselibs.annotation.DatabaseColumn;
import com.zyc.baselibs.annotation.DatabaseTable;
import com.zyc.baselibs.dao.SqlScriptCommander;
import com.zyc.baselibs.dao.SqlProviderSupport;
import com.zyc.baselibs.vo.Pagination;

public class SqlActionCommanderTest {
	static SqlScriptCommander commander  = null;
	
	@Before
	public void before() {
		commander = new SqlScriptCommander();
	}
	
	@Test
	public void insertTest() {
		_Test test = new _Test();
		String sql = null;
		try {
			sql = commander.insert(test);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
		}
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");

		sql = commander.insert(test);
		System.out.println(sql);
		assertEquals(sql.contains("insert into tests(username,description,id,createdat,version) values"), true);
	}
	
	@Test
	public void deleteTest() {
		_Test test = new _Test();
		String sql = null;
		try {
			sql = commander.delete(test);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
		}
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");

		sql = commander.delete(test);
		System.out.println(sql);
		assertEquals(sql.contains("delete from tests where 1=1  and username="), true);
	}
	
	@Test
	public void updateTest() {
		_Test test = new _Test();
		String sql = commander.update(test);
		assertEquals(sql.contains("update tests set username="), true);
		_Test2 test2 = new _Test2();
		try {
			sql = commander.update(test2);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
			assertEquals(e.getMessage().contains("Cannot find the"), true);
		}

		_Test3 test3 = new _Test3();
		try {
			sql = commander.update(test3);
		} catch (Exception e) {
			assertEquals(e instanceof RuntimeException, true);
			assertEquals(e.getMessage().contains("The primary key"), true);
		}
		
		_Test4 test4 = new _Test4();
		sql = commander.update(test4);
		System.out.println(sql);
		assertEquals(sql.contains("update test4s set name="), true);
	}
	
	@Test
	public void selectTest() {
		_Test test = new _Test();
		String sql = commander.select(test);
		assertEquals(sql.contains("select * from tests where 1=1 "), true);
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");

		sql = commander.select(test);
		System.out.println(sql);
		assertEquals(sql.contains("select * from tests where 1=1  and username="), true);
	}
	
	@Test
	public void selectByPageTest() {

		_Test test = new _Test();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(SqlProviderSupport.PARAM_KEY_ENTITY, test);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(0, 0, null, false));
		String sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("limit 1,20"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(0, 1, null, false));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("limit 1,1"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(1, 0, null, false));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("limit 1,20"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(1, 1, "name", true));
		sql = commander.selectByPage(param);
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("order by username asc limit 1,1"), true);
		
		test.setId("123");
		test.setName("admin");
		test.setDescription("test");
		test.setCreatedat(new Date());
		test.setVersion("0");
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(0, 0, null, false));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("version=#{version,jdbcType=VARCHAR} limit 1,20"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(0, 1, null, false));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("version=#{version,jdbcType=VARCHAR} limit 1,1"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(1, 0, null, false));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("version=#{version,jdbcType=VARCHAR} limit 1,20"), true);
		
		param.put(SqlProviderSupport.PARAM_KEY_PAGINATION, new Pagination(1, 1, "name", true));
		sql = commander.selectByPage(param);
		System.out.println(sql);
		assertEquals(sql.contains("version=#{version,jdbcType=VARCHAR} order by username asc limit 1,1"), true);
	}
	
	@Test
	public void loadTest() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(SqlProviderSupport.PARAM_KEY_ID, "a");
		
		param.put(SqlProviderSupport.PARAM_KEY_CLASS, _Test.class);
		String sql = commander.load(param);
		System.out.println(sql);
		assertEquals(sql.contains("select * from tests") && sql.contains("and id=#{id,jdbcType=VARCHAR}"), true);
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