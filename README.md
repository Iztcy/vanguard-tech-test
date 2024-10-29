To build the project, run:
mvn install -DskipTests

Example of Import csv endpoint(POST REQUEST):
localhost:8080/api/import

Example of Get game sales endpoint(GET REQUEST):
localhost:8080/api/getGameSales?fromDate=2024-04-01&toDate=2024-04-31&minSalePrice=1&maxSalePrice=200&pageNo=10


Example of Get total sales endpoint(GET REQUEST):
localhost:8080/api/getTotalSales?fromDate=2024-04-11&toDate=2024-04-31&gameNo=10
