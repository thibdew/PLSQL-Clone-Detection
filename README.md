# PL/SQL Clone Detection
Clone Detection with PMD (token based) and Clone Digger (tree based).


## PMD
### Usage
##### GUI

```sh
$ cd tool_pmd/bin
$ ./run.sh cpdgui
```

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


## Categorizing clones, displaying clones (HTML), ...
Coming soon.