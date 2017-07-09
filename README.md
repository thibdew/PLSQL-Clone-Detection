# PL/SQL Clone Detection
Clone Detection with PMD (token based) and Clone Digger (tree based).


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
$ cd tool_clonedigger
$ sudo python setup.py install --user 
```

Will install in default location ~/Library/Python/2.7/site-packages/ and add 'clonedigger' command to /usr/local/bin.

### Usage
```sh
$ clonedigger -l plsql --cpd-output Path/To/PLSQL-Queries
```
Generates output.xml with clones. 


## Categorizing clones, displaying clones (HTML), combining clone instances
Some helpertools are created:
* **Create Query Files** - To create single SQL-files from teh exported query list by CALI 
* **Combine Query instances** - Clone Digger outputs clones is pairs instead of classes. This tool can be used to create classes from those pairs.
* **Categorize Clones** - Create three categories of clones
* **Easely view clones** - Use this tool to generate an interactive HTML-file to easily view the clones

The usage and installation of these tools is explained in the README file in each corresponding directory.