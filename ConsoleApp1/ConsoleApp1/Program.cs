// See https://aka.ms/new-console-template for more information
var listBuckets = new testing.ListBuckets();

var future = listBuckets.listBuckets("XXX", "YYY", "localhost", 8080);

future.join();

Console.ReadLine();
