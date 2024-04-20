<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-mapping>
    <class name="${entity.tableName}" table="${entity.tableName}" optimistic-lock="version">
		<#if entity.columns?exists>
			<#list entity.columns as attr>
                <#if attr.dbFieldName == "id">
                    <#if entity.jformPkType?if_exists?html=='UUID'>
						<id name="id" type="java.lang.String" length="${attr.dbLength}" unsaved-value="null">
                            <generator class="uuid"/>
                        </id>
                    <#elseif entity.jformPkType?if_exists?html=='MJID'>
                        <id name="id" type="java.lang.String" length="${attr.dbLength}" unsaved-value="null">
                            <generator class="uuid"/>
                        </id>
                    <#elseif entity.jformPkType?if_exists?html=='NATIVE'>
                        <#if dataType=='MYSQL'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="identity"/>
                            </id>
                        <#elseif dataType=='ORACLE'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="native"/>
                            </id>
                        <#elseif dataType=='SQLSERVER'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="identity"/>
                            </id>
                        <#elseif dataType=='POSTGRESQL'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="native"/>
                            </id>
                        </#if>
                    <#elseif entity.jformPkType?if_exists?html=='SEQUENCE'>
                        <#if dataType=='MYSQL'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="identity"/>
                            </id>
                        <#elseif dataType=='ORACLE'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="sequence">
                                    <param name="sequence">${entity.jformPkSequence}
                                    </param>
                                </generator>
                            </id>
                        <#elseif dataType=='SQLSERVER'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="identity"/>
                            </id>
                        <#elseif dataType=='POSTGRESQL'>
							<id name="id" type="java.lang.Long" length="${attr.dbLength}" unsaved-value="null">
                                <generator class="native"/>
                            </id>
                        </#if>
                    <#else>
						<id name="id" type="java.lang.String" length="${attr.dbLength}" unsaved-value="null">
                            <generator class="uuid"/>
                        </id>
                    </#if>

                <#else>
					<property name="${attr.dbFieldName}"
						<#switch attr.dbType?lower_case>
                            <#case "string">
								type="java.lang.String"
                                <#break>
                            <#case "text">
                            <#-- update--begin--author:scott Date:20180227 for:针对oracle情况下text类型采用clob转换 -->
                                <#if dataType=='ORACLE'>
									type="clob"
                                <#else>
									type="text"
                                </#if>
                            <#-- update--end--author:scott Date:20180227 for:针对oracle情况下text类型采用clob转换 -->
                                <#break>
                            <#case "int">
								type="java.lang.Integer"
                                <#break>
                            <#case "bigint">
								type="java.lang.Long"
                                <#break>
                            <#case "double">
                                <#if dataType=='MYSQL'>
									type="java.lang.Double"
                                <#elseif dataType=='ORACLE'>
									type="java.math.BigDecimal"
                                <#elseif dataType=='POSTGRESQL'>
									type="java.math.BigDecimal"
                                <#elseif dataType=='SQLSERVER'>
									type="java.math.BigDecimal"
                                </#if>
                                <#break>
                            <#case "date">
                                <#if dataType=='MYSQL'>
									type="java.sql.Date"
                                <#elseif dataType=='ORACLE'>
									type="java.sql.Timestamp"
                                <#elseif dataType=='POSTGRESQL'>
									type="java.util.Date"
                                <#elseif dataType=='SQLSERVER'>
									type="java.util.Date"
                                </#if>
                                <#break>
                            <#case "datetime">
                                <#if dataType=='MYSQL'>
                                    type="java.sql.Timestamp"
                                <#elseif dataType=='ORACLE'>
                                    type="java.sql.Timestamp"
                                <#elseif dataType=='POSTGRESQL'>
                                    type="java.util.Date"
                                <#elseif dataType=='SQLSERVER'>
                                    type="java.util.Date"
                                </#if>
                                <#break>
                            <#case "time">
                                <#if dataType=='MYSQL'>
                                    type="java.sql.Time"
                                <#elseif dataType=='ORACLE'>
                                    type="java.sql.Timestamp"
                                <#elseif dataType=='POSTGRESQL'>
                                    type="java.util.Date"
                                <#elseif dataType=='SQLSERVER'>
                                    type="java.util.Date"
                                </#if>
                                <#break>
                            <#case "bigdecimal">
                                <#if dataType=='MYSQL'>
									type="java.math.BigDecimal"
                                <#elseif dataType=='ORACLE'>
									type="java.math.BigDecimal"
                                <#elseif dataType=='POSTGRESQL'>
									type="java.math.BigDecimal"
                                <#elseif dataType=='SQLSERVER'>
									type="java.math.BigDecimal"
                                </#if>
                                <#break>
                            <#case "blob">
                                <#if dataType=='MYSQL'>
									type="blob"
                                <#elseif dataType=='ORACLE'>
							 		type="blob"
                                <#elseif dataType=='POSTGRESQL'>
									type="binary"
                                <#elseif dataType=='SQLSERVER'>
									type="image"
                                </#if>
                                <#break>
                        </#switch> access="property">
                        <column name="${attr.dbFieldName}"
							<#if dataType=='SQLSERVER' && attr.dbType?lower_case="string">
                                sql-type="nvarchar(${attr.dbLength})" </#if>
							<#if dataType=='SQLSERVER' && attr.dbType?lower_case="text"> sql-type="ntext" </#if>
							<#if attr.dbType=='double'||attr.dbType=='BigDecimal'>
							precision="${attr.dbLength}" scale="${attr.dbPointLength}"
                                <#else>length="${attr.dbLength}"</#if>
							<#if attr.dbDefaultVal?exists&&attr.dbDefaultVal!=''>default="${attr.dbDefaultVal}"</#if>
                                not-null="<#if attr.dbIsNull == 1>false<#else>true</#if>" unique="false">
                            <comment>${attr.dbFieldTxt}</comment>
                        </column>
                    </property>
                </#if>
            </#list>
        </#if>
    </class>


</hibernate-mapping>
