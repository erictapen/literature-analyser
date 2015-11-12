This is a small Java application, which turns a natural language text into a tree structure, which can be visualized later.

# Usage
You can download a release, or build your own from sources.

## Usage of a release
Download the latest release at [this page]( https://github.com/erictapen/literature-analyser/releases). Then open a terminal and navigate into the directory, where the files are located, e.g. with `cd Downloads/`. Now run literature-analyser by entering the following command:
```
java -jar literature-analyser-X.X.X-static-dependencies.jar -i example.txt -o example 
```
Please make sure, to replace `X.X.X` with your version. If the program successfully ran, you will find two new files in your directory: `example.dot` and `example.sentences`. The first one defines a tree structure and can be visualized by programs like graphviz or [weird-tree-plot](https://github.com/erictapen/weird-tree-plot). The second lists all processed sentences, which start with your defined keyword.
For a list of all possible options, run `java -jar literature-analyser-X.X.X-static-dependencies.jar --help`.

## Building from source
You will need Maven and Git. If you don't know how to get it, you might be lucky by typing `sudo apt-get install mvn git` into a terminal. Then enter the following:
```
git clone https://github.com/erictapen/literature-analyser
cd literature-analyser
mvn package
```
Your Jar file lies now in `target/`. Execute it with
```
java -jar target/literature-analyser-X.X.X-static-dependencies.jar -i data/example.txt -o out/example
```
Your output lies in `out/`. 
