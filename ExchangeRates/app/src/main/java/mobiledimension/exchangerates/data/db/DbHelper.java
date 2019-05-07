package mobiledimension.exchangerates.data.db;

public interface DbHelper {

    void setDataBase(String strDate, String currency, String json);

    String getStrPostModel(String strDate, String currency);
}
