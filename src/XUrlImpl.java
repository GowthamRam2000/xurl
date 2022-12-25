import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XUrlImpl implements XUrl {
    private Map<String, String> shortToLongUrlMap;

    // Map to store the mapping between long and short URLs
    private Map<String, String> longToShortUrlMap;

    // Set of characters to use for generating short URLs
    private static final String SHORT_URL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    // Length of the short URL
    private static final int SHORT_URL_LENGTH = 9;

    // Random number generator for generating short URLs
    private Random random;
    private Map<String, Integer> hitCountMap = new HashMap<>();


    public XUrlImpl() {
        this.shortToLongUrlMap = new HashMap<>();
        this.longToShortUrlMap = new HashMap<>();
        this.random = new Random();
    }
    private String generateRandomShortUrl() {
        String shortUrl = "http://short.url/";

        // Generate a random alphanumeric string of length 9
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int r = (int) (Math.random() * 62);
            if (r < 10) {
                sb.append((char) (r + '0'));
            } else if (r < 36) {
                sb.append((char) (r - 10 + 'A'));
            } else {
                sb.append((char) (r - 36 + 'a'));
            }
        }

        shortUrl += sb.toString();

        // Check if the short URL is already registered
        while (shortToLongUrlMap.containsKey(shortUrl)) {
            // If the short URL is already registered, generate a new one
            shortUrl = generateRandomShortUrl();
        }

        return shortUrl;
    }



    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        // Check if the short URL is already registered
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            // Return null if the short URL is already registered
            return null;
        } else {
            // Store the mapping in both maps
            shortToLongUrlMap.put(shortUrl, longUrl);
            longToShortUrlMap.put(longUrl, shortUrl);

            // Initialize the hit count for the short URL to 0
            hitCountMap.put(shortUrl, 0);

            // Return the short URL
            return shortUrl;
        }
    }
    @Override
    public String registerNewUrl(String longUrl) {
        // Generate a unique short URL
        String shortUrl = generateRandomShortUrl();

        // Store the mapping in both maps
        shortToLongUrlMap.put(shortUrl, longUrl);
        longToShortUrlMap.put(longUrl, shortUrl);

        // Initialize the hit count for the short URL to 0
        hitCountMap.put(shortUrl, 0);

        // Return the short URL
        return shortUrl;
    }
    @Override
    public String getUrl(String shortUrl) {
        // Get the long URL corresponding to the short URL
        String longUrl = shortToLongUrlMap.get(shortUrl);

        // If the short URL is not registered, return null
        if (longUrl == null) {
            return null;
        }

        // Increment the hit count for the short URL
        int hitCount = hitCountMap.get(shortUrl);
        hitCountMap.put(shortUrl, hitCount + 1);

        // Return the long URL
        return longUrl;
    }

    //@Override
    public Integer getHitCount(String longUrl) {
        // Get the short URL corresponding to the long URL
        String shortUrl = longToShortUrlMap.get(longUrl);

        // If the long URL is not registered, return 0
        if (shortUrl == null) {
            return 0;
        }

        // Remove the mapping between the long URL and the short URL
        longToShortUrlMap.remove(longUrl);
        shortToLongUrlMap.remove(shortUrl);

        // Return the hit count for the short URL
        return hitCountMap.get(shortUrl);
    }
    @Override
    public String delete(String longUrl) {
        // Get the short URL corresponding to the long URL
        String shortUrl = longToShortUrlMap.get(longUrl);

        // If the long URL is not registered, return null
        if (shortUrl == null) {
            return null;
        }

        // Remove the mapping between the long URL and the short URL
        longToShortUrlMap.remove(longUrl);
        shortToLongUrlMap.remove(shortUrl);

        // Return the short URL
        return shortUrl;
    }


}

