# Documents Search Engine

##	Introduction
This document describes Documents Search Engine app. The search engine is a system for managing indexing and querying for documents.


This document is intended to be read by people who want to get insights how the app works.


#	Overview
##	Purpose of the Software
The primary scope of the component is to take an index and query documents.


# Software Design

## Technology Stack
1. Java SE 11
2. Apache Lucene
3. PicoContainer
4. Lombok
5. Apache Maven


## FUNCTIONAL DESCRIPTION

### Overview

A search engine contains a set of documents. Each document consists of a unique ID and a list of tokens. The search engine responds to queries by finding documents which contain certain tokens and returning their IDs.

The program should accept a sequence of commands from standard input and respond on standard output. Commands are terminated by newlines. There are two types of commands:

#### 1- The index command

**index** doc-id token1 … tokenN

The index command adds a document to the index. The doc-id is an integer. Tokens are arbitrary alphanumeric strings. A document can contain an arbitrary number of tokens greater than zero. The same token may occur more than once in a document. If the doc-id in an index command is the same as in a previously seen index command, the previously stored document should be completely replaced (i.e., only the tokens from the latest command should be associated with the doc-id).

When the program successfully processes an index command, it should output:

**index** ok doc-id

If the program sees an invalid index command (e.g, a token contains a non-alphanumeric character, or a doc-id is not an integer) it should report an error to standard output and continue processing commands. The error output should have the following form:

**index** error error message

#### 2- The query command

Where expression is an arbitrary expression composed of alphanumeric tokens and the special symbols &, |, (, and ). The most simple expression is a single token, and the result of executing this query is a list of the IDs of the documents that contain the token. More complex expressions can be built built using the operations of set conjunction (denoted by &) and disjunction (denoted by |). The & and | operation have equal precedence and are commutative and associative. Parentheses have the standard meaning. Parentheses are mandatory: a | b | c is not valid, (a | b) | c must be used (this is to make parsing queries simpler).

Logically, to execute the query the program looks at every document previously specified by the index command, checks if the document matches the query, and outputs the doc-id if it does. However this is suboptimal and much more efficient implementations exist.

Upon reading the query command the program should execute the query and produce the following output:

**query** results doc-id1 doc-id2 …

The doc-ids of the matching documents can be output in arbitrary order. If there is an error, the output should be:

**query** error _error message_


### Assumtions
- Query speed is more important than indexing speed.

## Architecture

![Architecture Flow](flow-diagram.png?sanitize=true)

## Use Cases

List of use cases
1. Use Case Index Documents

![Index Documents Use Case](index-documents.png?sanitize=true)

2. Use Case Query Documents

![Query Documents Use Case](query-documents.png?sanitize=true)



##	API
The API is console based. User provides the commands in string format and gets the response in standard output.

# Data Model
This component persists data in memory using Apache Lucene. </br>

Aache Lucene benefits:
- scalable, high-performance indexing
- small RAM requirements -- only 1MB heap
- incremental indexing as fast as batch indexing
- index size roughly 20-30% the size of text indexed

## Security
##	Authentication
###	Authentication of users
|Role|	Authentication	|Comment|
| ------------- |:-------------:| -----:|
|Anonymous |	None|	 the app uses no authentication for communication|



###	Authorization of users
|Role|	Authorization|	Comment|
| ------------- |:-------------:| -----:|
|Anonymous| 	None|	the app uses no authorization for communication|

## I18N

Internationaliaztion provided for the app. Provide the language and country as VM options.

###	Logging
No logging provided for the app. Logging retetion policy not applicable.

###  Running the application

To run the application execute the following command.

```
mvn clean install

java -jar ./target/documents-search-engine-1.0.jar
```

### Example

![Example](example.png?sanitize=true)
