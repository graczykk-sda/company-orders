## Requirements

```
Java 17
```

## Build

```
./gradlew clean build
```

## Execute

```
./orders.sh <csv_file1> ... <csv_fileN>
```

## Example data
Example test csv files are located in `test-data` folder, so you can execute it program this way:

```
./orders.sh test-data/products_1.csv test-data/products_2.csv test-data/products_3.csv
```