# Combine Clone Instances (for Clone Digger)
Clone Digger outputs the clones in pairs instead of classes (as is the case with PMD).  This tools can transform the pairs to clone classes.

## Installation
```sh
$ helpertool_combine_clone_instances
$ javac *.java
```

## Usage
```sh
$ cd helpertool_combine_clone_instances
$ java CombineClone [CloneDiggerCloneFile.xml]
```
`[CloneDiggerCloneFile.xml]` is the location of a XML-file Clones found by Clone Digger.
It creates `generatedoutput.xml` as output. It may take a while to complete.