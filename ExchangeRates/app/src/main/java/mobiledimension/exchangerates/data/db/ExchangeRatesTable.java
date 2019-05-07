package mobiledimension.exchangerates.data.db;

public class ExchangeRatesTable {

    public static final String TABLE = " ExchangeRatesTable";

     static  class COLUMN {
         static final String DATE = "date";
         static final String CURRENCY = "currency";
         static final String JSON = "json";
    }

    public static  final  String CREATE_SCRIPT = "create table ExchangeRatesTable ("
            +  "date int,"
            + "currency text,"
            + "json text,"
            + "PRIMARY KEY(date, currency)"
            + ");";


}
