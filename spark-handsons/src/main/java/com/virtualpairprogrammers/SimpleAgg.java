package com.virtualpairprogrammers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.min;

public class SimpleAgg {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().appName("SimpleAgg")
                .master("local[*]")
                .getOrCreate();
        Dataset<Row> studentsDset = sparkSession.read().option("header",true).csv(args[0]);
        Dataset<Row> dataset = studentsDset.groupBy("subject").agg(max("score").alias("max_score"),min("score").alias("min_score"));
        dataset.select("subject");
        dataset.show();

    }
}
