hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings
environments {
	development {
		dataSource {
			pooled = true
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			url="jdbc:mysql://localhost:3306/technique_finder?useUnicode=true&characterEncoding=utf-8"
			username="tf_admin"
			password="<password>"
		}
	}
	test {
		dataSource {
			driverClassName = "org.hsqldb.jdbcDriver"
			dialect = ""
			username = "sa"
			password = ""
			dbCreate = "create-drop"
			url = "jdbc:hsqldb:mem:testDb"
		}
	}
	production {
		dataSource {
			pooled = true
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
			properties {
		        minEvictableIdleTimeMillis = 1800000 // every 30 mins
		        timeBetweenEvictionRunsMillis = 1800000 // every 30 mins
	        }
		}
	}
}
