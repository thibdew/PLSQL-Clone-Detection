# Create query files
For the queries to be used by the clone detection tools they need to be split up in single SQL-files. This tool can generate single files by giving it query list exported by CaLi. 
Example:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<RESULTS>
	<query>
		<queryname>V_A_TC_VEX_INS_NT_VEX</queryname>
		<sql><![CDATA[ SELECT ... ]]></sql>
	</query>
	...
```

## Installation
```sh
$ cd helpertool_create_query_files
$ javac *.java
```

## Usage
```sh
$ cd helpertool_create_query_files
$ java CreateQueryFiles [ExportedQueryList.xml] [Export/Path/]
```
`[ExportedQueryList.xml]` is the location of a XML-file with all queries exported by CaLi.
`[Export/Path/]` is the path where the files need to be stored.