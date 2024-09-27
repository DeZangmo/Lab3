package main.java.org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    private static final String COUNTRY_CODE_KEY = "code";
    private List<String> countryCodes;
    private JSONArray countryData;
    // Task: pick appropriate instance variables for this class

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        countryCodes = new ArrayList<>();
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < countryData.length(); i++) {
                JSONObject country = countryData.getJSONObject(i);
                jsonArray.add(country.getString(COUNTRY_CODE_KEY).toLowerCase());
            }
            // Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        List<String> languages = new ArrayList<>();
        for (int i = 0; i < countryData.length(); i++) {
            JSONObject countryObj = countryData.getJSONObject(i);
            if (countryObj.getString(COUNTRY_CODE_KEY).equalsIgnoreCase(country)) {
                for (String key : countryObj.keySet()) {
                    if (!key.equalsIgnoreCase(COUNTRY_CODE_KEY)) {
                        languages.add(key.toLowerCase());
                    }
                }
                break;
            }
        }
        // Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        return languages;
    }

    @Override
    public List<String> getCountries() {
        // Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String country, String language) {
        String translation = "Country not found";
        for (int i = 0; i < countryData.length(); i++) {
            JSONObject countryObj = countryData.getJSONObject(i);
            if (countryObj.getString(COUNTRY_CODE_KEY).equalsIgnoreCase(country)) {
                if (countryObj.has(language)) {
                    translation = countryObj.getString(language);
                }
                else {
                    translation = "Translation not found ";
                }
            }
        }
        // Task: complete this method using your instance variables as needed
        return translation;
    }
}
