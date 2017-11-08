package com.group.project.util;

import com.group.project.model.UserModel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CreateObjectByDB {


	private static String tableName="table_name";
	public static void main(String[] args) throws Exception{
//		soutFiled();
		getSQL();
//		UserModel model=new UserModel();
//		getModelXml(model);
	}


	private static String url = "jdbc:mysql://1.1.1.1:3306/xxx?useUnicode=true&characterEncoding=utf8";
	private static String username = "root";
	private static String password = "admin";

	private static Connection connection;
	private static DatabaseMetaData metaData;
	private static ResultSet resultSet;

	static {
		try {
			connection=DriverManager.getConnection(url, username, password);
			metaData = connection.getMetaData();
			resultSet = metaData.getColumns(null, null, tableName, null);
		} catch (SQLException e) {
			System.out.println("初始化失败!");
			e.printStackTrace();
		}
	}



    private static void getSQL() throws Exception {
		findByIdSQL();
		insertSQL();
		updateSQL();
		connection.close();
	}

	/**
	 * 输入表名，生成成员变量
	 */
    private static void soutFiled() throws Exception{
    	resultSet.beforeFirst();
		System.out.println("private static final long serialVersionUID = 1L;");
		while(resultSet.next()){
			if ("update_time/create_time/create_user/update_user".contains(resultSet.getString("COLUMN_NAME").toLowerCase())){
				continue;
			}
			if (Utils.isNotEmpty(resultSet.getString("REMARKS"))){
				System.out.println("/**"+resultSet.getString("REMARKS")+"*/");
			}
			if (resultSet.getString("TYPE_NAME").equals("VARCHAR")){
				System.out.println("private String "+getFiled(resultSet.getString("COLUMN_NAME"))+";");
			}
			if (resultSet.getString("TYPE_NAME").equals("DECIMAL")){
				System.out.println("private Double "+getFiled(resultSet.getString("COLUMN_NAME"))+";");
			}
			if ("TIMESTAMP/TINYINT".contains(resultSet.getString("TYPE_NAME"))){
				System.out.println("private Integer "+getFiled(resultSet.getString("COLUMN_NAME"))+";");
			}
		}
		connection.close();
	}
    
    private static void findByIdSQL() throws Exception{
    	resultSet.beforeFirst();
    	System.out.println("	<select id=\"findById\" parameterType=\"java.lang.Integer\" resultMap=\"XXX\">");
    	System.out.println("		SELECT");
    	while(resultSet.next()){
    		if (resultSet.isFirst()){
				System.out.print("			");
			}
    		if(resultSet.isLast())
    			System.out.println(resultSet.getString("COLUMN_NAME").toLowerCase());
    		else
    			System.out.print(resultSet.getString("COLUMN_NAME").toLowerCase()+",");
    	}
		resultSet.beforeFirst();
    	resultSet.next();
    	System.out.println("		FROM");
    	System.out.println("			"+tableName);
    	System.out.println("		WHERE "+resultSet.getString("COLUMN_NAME").toLowerCase()+
										" = #{"+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+"}");
    	System.out.println("	</select>");
    	System.out.println();
    }

    private static void insertSQL() throws Exception{
    	resultSet.beforeFirst();
    	System.out.println("	<insert id=\"insertBySelective\" parameterType=\"XXX.XXX.XXX\">");
    	System.out.println("		insert into "+tableName);
    	System.out.println("		<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
    	while(resultSet.next()){
			System.out.println("			<if test=\""+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+" != null\">");
			System.out.println("				"+resultSet.getString("COLUMN_NAME").toLowerCase()+",");
			System.out.println("			</if>");
    	}
		System.out.println("		</trim>");
		resultSet.beforeFirst();
		System.out.println("		<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
		while(resultSet.next()){
			System.out.println("			<if test=\""+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+" != null\">");
			System.out.println("				#{"+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+"},");
			System.out.println("			</if>");
		}
		System.out.println("		</trim>");
		System.out.println("	</insert>");
		System.out.println();
	}

    private static void updateSQL() throws Exception{
    	resultSet.beforeFirst();
    	System.out.println("	<update id=\"updateBySelective\" parameterType=\"XXX.XXX.XXX\">");
    	System.out.println("		update "+tableName);
    	System.out.println("		<set>");
    	while(resultSet.next()){
			System.out.println("			<if test=\""+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+" != null\">");
			System.out.println("				"+resultSet.getString("COLUMN_NAME").toLowerCase()+" = #{"+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+"},");
			System.out.println("			</if>");
    	}
		System.out.println("		</set>");
		resultSet.beforeFirst();
		resultSet.next();
		System.out.println("		where "+resultSet.getString("COLUMN_NAME").toUpperCase()+" = #{"+getFiled(resultSet.getString("COLUMN_NAME").toLowerCase())+"}");
		System.out.println("	</update>");
		System.out.println();
	}

	private static String getFiled(String string){
		StringBuilder column = new StringBuilder();
		for (int i = 0; i < string.length(); i++) {
			if ("_".equals(String.valueOf(string.charAt(i)))){
				column.append(Character.toUpperCase(string.charAt(++i)));
				continue;
			}
			column.append(string.charAt(i));
		}
		return column.toString();
	}

	/**通过对象获取Xml的resultMap，复杂对象需再简单修改*/
	public static void getModelXml(Object obj) throws Exception {
		Class<? extends Object> objClass = obj.getClass();
		System.out.println("    <resultMap id=\"" + objClass.getSimpleName() + "\" type=\"" + objClass.getCanonicalName() + "\">");
		getEachProperty(objClass.newInstance());
		System.out.println("    </resultMap>");
	}

	private static void getEachProperty(Object obj) throws Exception{
		Field[] field = obj.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			StringBuilder column = new StringBuilder();
			String property = "";
			String jdbcType = "";
			property = field[j].getName();
			if (property.equals("serialVersionUID"))
				continue;
			for (int i = 0; i < property.length(); i++) {
				if (Character.isUpperCase(property.charAt(i)))
					column.append("_");
				column.append(Character.toUpperCase(property.charAt(i)));
			}
			String type = field[j].getGenericType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String"))
				jdbcType = "VARCHAR";
			else if (type.equals("class java.lang.Integer")||type.equals("int"))
				jdbcType = "INTEGER";
			else if (type.equals("class java.lang.Boolean"))
				jdbcType = "BOOLEAN";
			else if (type.equals("byte"))
				jdbcType = "BYTE";
			else if (type.equals("long"))
				jdbcType = "LONG INTEGER";
			else if (type.equals("float"))
				jdbcType = "FLOAT";
			else if (type.equals("double"))
				jdbcType = "DOUBLE";
			else if (type.equals("class java.util.Date"))
				jdbcType = "TIMESTAMP";
			else if (type.contains("java.util.List")){
				System.out.println("<collection  property=\""+property+"\" resultMap=\""+getClassName(type)+"\"></collection>");
				getEachProperty(Class.forName(getFullClassName(type)).newInstance());//获取子对象实例
				System.out.println();
				continue;
			}else{
				System.out.println("<association property=\""+property+"\" javaType=\""+getClassName(type)+"\"/></association>");
				getEachProperty(Class.forName(getFullClassName(type)).newInstance());//获取子对象实例
				System.out.println();
				continue;
			}
			System.out.println("        <result column=\"" + column + "\" property=\"" + property + "\" jdbcType=\""
					+ jdbcType + "\"/>");
//			System.out.print(column+",");
		}
	}

	private static String getFullClassName(String type){
		if(type.contains("<")){//集合
			return type.split(">")[0].split("<")[1];
		}else{//对象
			return type.substring(6, type.length());
		}
	}
	private static String getClassName(String type){
		if(type.contains("<")){
			String[] split = type.split("\\.");
			String name = split[split.length-1];
			return name.substring(0, name.length()-1);
		}
		else{
			String[] split = type.split("\\.");
			String name = split[split.length-1];
			return name;
		}
	}
}