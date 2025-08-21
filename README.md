# Compile specific file
## Scanner
```
make
make scanner FILE=<FILENAME>.go
```

## Parser
```
make
make parser FILE=<FILENAME>.go
```

## Parser with tree
```
make
make parserGui FILE=<FILENAME>.go
```

## Checker with print tree
```
make
make run FILE=<FILENAME>.go
```

# Run Tests
## Scanner
```cd tests && bash test_scanner.sh```

## Parser
```cd tests && bash test_parser.sh```

## Checker
```cd tests && bash test_checker.sh```