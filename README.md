# PL/SQL Clone Detection
Clone Detection with PMD (token based) and Clone Digger (tree based).

## Contents
1. PMD
2. Clone Digger
3. Typical usage pattern
4. Helper tools





## PMD
### Usage
##### GUI

```sh
$ cd tool_pmd/bin
$ ./run.sh cpdgui
```
In the GUI select the source folder containing the query-files. Select 'PL/SQL' as language. After getting the results, they can be saved as an XML-file by selecting 'File' > 'Save as XML'. 

##### CLI

```sh
$ cd tool_pmd/bin
$ ./run.sh cpd --minimum-tokens 75 --files Path/To/PLSQL-Queries --language plsql --format xml > output_PMD.xml
```
Generates output_PMD.xml with clones. 

More CLI options: http://pmd.sourceforge.net/snapshot/usage/cpd-usage.html





## Clone Digger
### Installation
```sh
$ cd tool_clonedigger/clonedigger/plsql_antlr
$ javac -cp antlr-4.6-complete.jar:. TreeProducer.java
$ jar -cfe TreeProducer.jar TreeProducer  *.class org/
$ rm *.class
```
Now copy the generated TreeProducer.jar to your working directory.

```sh
$ cd tool_clonedigger
$ sudo python setup.py install --user 
```
Will install in default location ~/Library/Python/2.7/site-packages/ and add 'clonedigger' command to /usr/local/bin.

### Usage
Now from the working directory (directory with the TreeProducer.jar), execute the following command.
```sh
$ clonedigger -l plsql --cpd-output [Path/To/PLSQL-Queries]
```
`[Path/To/PLSQL-Queries]` is the path to the folder with all SQL-files. This will generate a clones.xml file. Clone Digger may take a while to complete.




## Typical use pattern
A typical use pattern:
1. Convert the single XML-file with all the exported queries to separate SQL-files. *See 'helpertool_create_query_files'.*
2. Use PMD or Clone Digger on the SQL-files to generate an clone.xml file with all found clones.
3. If Clone Digger has been used, convert the output to clone classes. *See 'helpertool_combine_clone_instances'.*
4. Categorize the foundclones based on some heuristics. *See 'helpertool_categorize_clones'.*
5. Create an HTML-file to easily view all found clones. *See 'helpertool_clone_viewer'.*





## Categorizing clones, displaying clones (HTML), combining clone instances
Some helpertools are created:
* **Create Query Files** - To create single SQL-files from the exported query list by CALI.
* **Combine Query instances** - Clone Digger outputs clones is pairs instead of classes. This tool can be used to create classes from those pairs.
* **Categorize Clones** - Create three categories of clones.
* **Easely view clones** - Use this tool to generate an interactive HTML-file to easily view the clones.

The usage and installation of these tools is explained in the README file in each corresponding directory.