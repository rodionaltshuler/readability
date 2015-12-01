package com.ottamotta.readability.library.manager;

import android.test.AndroidTestCase;

import com.ottamotta.readability.common.network.ReadabilityRestAdapter;
import com.ottamotta.readability.library.entity.Bookmark;
import com.ottamotta.readability.library.manager.network.BookmarkRestServiceWrapper;
import com.ottamotta.readability.library.manager.network.MockRetrofitClient;

import java.util.List;

public class BookmarksRepositoryTest extends AndroidTestCase {

    public static final String EMPTY_BOOKMARKS_RESPONSE = "{}";

    private MockRetrofitClient mockClient;

    private BookmarksRepository repository;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockClient = new MockRetrofitClient();
        ReadabilityRestAdapter mockRestAdapter = ReadabilityRestAdapter.newMockRestAdapter(mockClient);
        BookmarkRestServiceWrapper restServiceWrapper = new BookmarkRestServiceWrapper(mockRestAdapter);
        repository = new BookmarksRepository(restServiceWrapper, new BookmarksMemoryCache());
    }

    public void testEmptyRepositoryAfterForceRefreshAndSuccessfullEmptyResponse() throws Exception {
        mockClient.setResponseString(EMPTY_BOOKMARKS_RESPONSE);
        mockClient.setStatusCode(200);
        boolean empty = repository.getAll(true).isEmpty();
        assertTrue("The repository is NOT empty after force refresh and getting empty result", empty);

    }

    public void testEmptyRepositoryAfterForceRefreshAndSuccessfullEmptyResponseNegative() throws Exception {
        mockClient.setResponseString(NON_EMPTY_BOOKMARKS_RESPONSE);
        mockClient.setStatusCode(200);
        boolean empty = repository.getAll(true).isEmpty();
        assertFalse("The repository is empty with bookmarks got from server", empty);
    }

    public void testCacheNotInvalidatedOnForceRefreshAfterServerError() throws Exception {
        mockClient.setResponseString(NON_EMPTY_BOOKMARKS_RESPONSE);
        mockClient.setStatusCode(200);
        List<Bookmark> initialBookmarks = repository.getAll(false); //put some in repo
        assertFalse("Got successfull server response with bookmarks, but empty bookmarks list returned", initialBookmarks.isEmpty());
        mockClient.setResponseString(EMPTY_BOOKMARKS_RESPONSE);
        mockClient.setStatusCode(404);
        try {
            repository.getAll(false);
        } catch (Exception e) {
            //ignore
        }
        assertFalse("Cached bookmarks from 1st request wrongly invalidated after 2nd request failed", repository.getAll(false).isEmpty());
    }

    //private static final List<Bookmark> simpleBookmarksList = Lists.newArrayList(new Bookmark(), new Bookmark());

    private final String NON_EMPTY_BOOKMARKS_RESPONSE = "{\n" +
            "    \"bookmarks\": [\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-27 09:47:46\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107803367,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"github.com\",\n" +
            "                \"title\": \"gabrielemariotti/cardslib\",\n" +
            "                \"url\": \"https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARD.md#use-your-content-inner-layout\",\n" +
            "                \"lead_image_url\": \"https://github.com/gabrielemariotti/cardslib/raw/master/demo/images/card/title.png\",\n" +
            "                \"author\": \"gabrielemariotti\",\n" +
            "                \"excerpt\": \"In this page you can find info about: Creating a base Card Creating a Card is pretty simple. First, you need an XML layout that will display the Card.&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 1667,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"pnfyk3yh\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/pnfyk3yh\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-27 09:47:46\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 22:27:48\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107783411,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"stackoverflow.com\",\n" +
            "                \"title\": \"Top Questions\",\n" +
            "                \"url\": \"http://stackoverflow.com/\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"Stack Overflow is a question and answer site for professional and enthusiast programmers. It's 100% free. Take the 2-minute tour Here's how it works: Anybody can ask a question Anybody can answer The&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 42,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"epp3mx8h\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/epp3mx8h\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 22:27:48\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 22:23:32\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107783196,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"dou.ua\",\n" +
            "                \"title\": \"Android дайджест #11: Слияние Chrome OS и Android, разработчики-миллионеры, отладка по Wi-Fi\",\n" +
            "                \"url\": \"http://dou.ua/lenta/digests/android-digest-11/\",\n" +
            "                \"lead_image_url\": \"http://s.developers.org.ua/img/announces/android6_hUQTIt8.png\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"&#x412;&#xA0;&#x432;&#x44B;&#x43F;&#x443;&#x441;&#x43A;&#x435;: &#x430;&#x43F;&#x43F;&#x430;&#x440;&#x430;&#x442;&#x43D;&#x43E;&#x435; &#x443;&#x441;&#x43A;&#x43E;&#x440;&#x435;&#x43D;&#x438;&#x435;,&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 436,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"kxpvtta5\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/kxpvtta5\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 22:23:32\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 22:10:58\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107782755,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.amazon.com\",\n" +
            "                \"title\": \"Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more\",\n" +
            "                \"url\": \"http://www.amazon.com/\",\n" +
            "                \"lead_image_url\": \"http://g-ecx.images-amazon.com/images/G/01/img15/consumer-electronics/egg/gw/25443_egg_gifted3_right_double-promo_2x._CB289659967_.jpg\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 30,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"ahpf0xzu\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/ahpf0xzu\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 22:10:58\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 22:02:45\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107782428,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"petrimazepa.com\",\n" +
            "                \"title\": \"Без барьера\",\n" +
            "                \"url\": \"http://petrimazepa.com/goodpetition.html\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": \"Радио \\\"Кома\\\"\",\n" +
            "                \"excerpt\": \"&#x410;&#x43B;&#x435;&#x43A;&#x441;&#x435;&#x439; &#x41A;&#x440;&#x430;&#x441;&#x43D;&#x43E;&#x449;&#x451;&#x43A;&#x43E;&#x432; 3 &#x434;&#x435;&#x43A;&#x430;&#x431;&#x440;&#x44F; &#x2013;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 999,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"lcmtmljn\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/lcmtmljn\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 22:02:45\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 21:55:08\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107782199,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"petrimazepa.com\",\n" +
            "                \"title\": \"Кино и немцы: немецкие СМИ\",\n" +
            "                \"url\": \"http://petrimazepa.com/sexandtheburg.html\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": \"Радио \\\"Кома\\\"\",\n" +
            "                \"excerpt\": \"&#x41C;&#x430;&#x433;&#x434;&#x430;&#x43B;&#x435;&#x43D;&#x430; &#x420;&#x43E;&#x439;&#x442; &#x41D;&#x430; &#x442;&#x451;&#x43C;&#x43D;&#x43E;&#x43C;-&#x442;&#x451;&#x43C;&#x43D;&#x43E;&#x43C;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 1051,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"38i2rwqx\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/38i2rwqx\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 21:55:08\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 19:37:29\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107777225,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"petrimazepa.com\",\n" +
            "                \"title\": \"Турция vs. Россия, сomeback Фирташа и в зоне АТО снова «Град». Дайджест 25 ноября\",\n" +
            "                \"url\": \"http://petrimazepa.com/pm-daily469.html\",\n" +
            "                \"lead_image_url\": \"http://petrimazepa.com/bundles/pim/images/storage/26_november_2015/1718112.jpg\",\n" +
            "                \"author\": \"Радио \\\"Кома\\\"\",\n" +
            "                \"excerpt\": \"&#xAB;&#x425;&#x430;&#x43C;&#x438;&#x442;&#x44C; &#x420;&#x43E;&#x441;&#x441;&#x438;&#x438; &#x2013; &#x441;&#x43C;&#x435;&#x440;&#x442;&#x435;&#x43B;&#x44C;&#x43D;&#x43E;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 2510,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"cv5jp7ft\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/cv5jp7ft\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 19:37:29\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 19:36:40\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107777167,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"novaposhta.ua\",\n" +
            "                \"title\": \"Новая Почта - экспресс доставка по Украине\",\n" +
            "                \"url\": \"https://novaposhta.ua/\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"23&#xA0;&#x43B;&#x438;&#x441;&#x442;&#x43E;&#x43F;&#x430;&#x434;&#x430;&#xA0;2015 &#x41F;&#x440;&#x43E; &#x440;&#x43E;&#x431;&#x43E;&#x442;&#x443;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 152,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"6jeneyik\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/6jeneyik\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 19:36:38\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 17:26:04\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107772198,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.petrimazepa.com\",\n" +
            "                \"title\": \"Пётр и Мазепа\",\n" +
            "                \"url\": \"http://www.petrimazepa.com/\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": \"Радио \\\"Кома\\\"\",\n" +
            "                \"excerpt\": \"&#x411;&#x44B;&#x442;&#x44C; &#x432; &#x43C;&#x430;&#x441;&#x43A;&#x435; (&#xA0;&#x41F;&#x443;&#x441;&#x442;&#x44B;&#x448;&#x43A;&#x430; .) &#xA0;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 417,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"g5ofwpcb\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/g5ofwpcb\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 17:26:03\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 17:22:23\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107772009,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"twitter.com\",\n" +
            "                \"title\": \"Sign up\",\n" +
            "                \"url\": \"http://twitter.com/\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"Sign up Log in Full name\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 55,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"c2mcfj82\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/c2mcfj82\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 17:22:23\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 16:24:18\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107769217,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"finance.ua\",\n" +
            "                \"title\": \"Финансы в Украине, Курсы валют, кредиты и депозиты, Банки, последние новости Украины\",\n" +
            "                \"url\": \"http://finance.ua/\",\n" +
            "                \"lead_image_url\": \"http://static.finance.ua/img/lib/e4/a/0d42b2f.jpg\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 1453,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"lnnqknnb\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/lnnqknnb\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 16:24:18\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:26:22\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107763444,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.facebook.com\",\n" +
            "                \"title\": \"http://www.facebook.com/\",\n" +
            "                \"url\": \"http://www.facebook.com/\",\n" +
            "                \"lead_image_url\": \"https://fbcdn-dragon-a.akamaihd.net/hphotos-ak-ash3/851565_602269956474188_918638970_n.png\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"Facebook logoEmail or PhonePasswordKeep me logged inForgot your password?\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 152,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"qt7ojkk3\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/qt7ojkk3\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:26:22\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:04:36\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107762233,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.velokiev.com\",\n" +
            "                \"title\": \"Велосипед как он есть и все о велосипедах\",\n" +
            "                \"url\": \"http://www.velokiev.com/\",\n" +
            "                \"lead_image_url\": null,\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"&#xA0;&#xA0;&#xA0;&#x418;&#x437;-&#x437;&#x430; &#x441;&#x440;&#x430;&#x432;&#x43D;&#x438;&#x442;&#x435;&#x43B;&#x44C;&#x43D;&#x43E; &#x43D;&#x435;&#x432;&#x44B;&#x441;&#x43E;&#x43A;&#x43E;&#x439;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 769,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"nozpydtj\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/nozpydtj\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:04:36\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:04:00\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107762204,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.gismeteo.ua\",\n" +
            "                \"title\": \"http://www.gismeteo.ua/\",\n" +
            "                \"url\": \"http://www.gismeteo.ua/\",\n" +
            "                \"lead_image_url\": \"http://s1.gismeteo.ua/images/news/fb765020f9a9a396f71d8e213f31ab15.jpg\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"Gismeteo.UAпрогноз погоды   Украина, RU / UA Россия, RU Lietuva, LT / RU Беларусь, RU\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 594,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"eblacxti\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/eblacxti\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:04:00\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:03:46\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107762198,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.linkedin.com\",\n" +
            "                \"title\": \"World's Largest Professional Network\",\n" +
            "                \"url\": \"http://www.linkedin.com/\",\n" +
            "                \"lead_image_url\": \"https://static.licdn.com/sc/h/5koy91fjbrc47yhwyzws65ml7\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"EmailEmailForgot password?Get started - it's free. Join LinkedInFirst name\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 49,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"azd8oggn\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/azd8oggn\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:03:46\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:02:23\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107762139,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"techcrunch.com\",\n" +
            "                \"title\": \"Cobalt Valkyrie: A Super Sleek Personal Aircraft\",\n" +
            "                \"url\": \"http://techcrunch.com/\",\n" +
            "                \"lead_image_url\": \"https://tctechcrunch2011.files.wordpress.com/2015/11/portland.jpg?w=210&h=158&crop=1&quality=85&strip=all\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"Crunch Network\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 808,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"l14vnhjb\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/l14vnhjb\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:02:23\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 14:00:42\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107762055,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"www.pravda.com.ua\",\n" +
            "                \"title\": \"Українська правда\",\n" +
            "                \"url\": \"http://www.pravda.com.ua/\",\n" +
            "                \"lead_image_url\": \"http://img.pravda.com/images/hromadskeradio.jpg\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"RSS PDA Facebook Twitter iPhone Заснована Георгієм Гонгадзе у 2000 році Знайти 15:51\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 745,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"h1cagb5m\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/h1cagb5m\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 14:00:42\",\n" +
            "            \"archive\": false\n" +
            "        },\n" +
            "        {\n" +
            "            \"user_id\": 1779941,\n" +
            "            \"read_percent\": \"0.00\",\n" +
            "            \"tags\": [],\n" +
            "            \"date_updated\": \"2015-11-26 13:04:45\",\n" +
            "            \"favorite\": false,\n" +
            "            \"id\": 107759609,\n" +
            "            \"date_archived\": null,\n" +
            "            \"date_opened\": null,\n" +
            "            \"article\": {\n" +
            "                \"domain\": \"dou.ua\",\n" +
            "                \"title\": \"Воспользуйтесь аккаунтом\",\n" +
            "                \"url\": \"http://dou.ua/\",\n" +
            "                \"lead_image_url\": \"http://s.developers.org.ua/img/announces/420_bp5qjKc.jpg\",\n" +
            "                \"author\": null,\n" +
            "                \"excerpt\": \"&#x413;&#x43B;&#x430;&#x432;&#x43D;&#x430;&#x44F; &#x424;&#x43E;&#x440;&#x443;&#x43C; &#x41B;&#x435;&#x43D;&#x442;&#x430; &#x417;&#x430;&#x440;&#x43F;&#x43B;&#x430;&#x442;&#x44B;&hellip;\",\n" +
            "                \"direction\": \"ltr\",\n" +
            "                \"word_count\": 363,\n" +
            "                \"date_published\": null,\n" +
            "                \"dek\": null,\n" +
            "                \"processed\": true,\n" +
            "                \"id\": \"osvrc914\"\n" +
            "            },\n" +
            "            \"article_href\": \"/api/rest/v1/articles/osvrc914\",\n" +
            "            \"date_favorited\": null,\n" +
            "            \"date_added\": \"2015-11-26 13:04:45\",\n" +
            "            \"archive\": false\n" +
            "        }\n" +
            "    ],\n" +
            "    \"meta\": {\n" +
            "        \"num_pages\": 1,\n" +
            "        \"page\": 1,\n" +
            "        \"item_count_total\": 18,\n" +
            "        \"item_count\": 18\n" +
            "    },\n" +
            "    \"conditions\": {\n" +
            "        \"opened_since\": null,\n" +
            "        \"added_until\": null,\n" +
            "        \"domain\": \"\",\n" +
            "        \"updated_until\": null,\n" +
            "        \"tags\": [],\n" +
            "        \"archive\": null,\n" +
            "        \"archived_until\": null,\n" +
            "        \"favorite\": null,\n" +
            "        \"opened_until\": null,\n" +
            "        \"archived_since\": null,\n" +
            "        \"favorited_since\": null,\n" +
            "        \"updated_since\": null,\n" +
            "        \"exclude_accessibility\": \"\",\n" +
            "        \"per_page\": 20,\n" +
            "        \"favorited_until\": null,\n" +
            "        \"order\": \"-date_added\",\n" +
            "        \"only_deleted\": null,\n" +
            "        \"page\": 1,\n" +
            "        \"added_since\": null,\n" +
            "        \"user\": \"rodionaltshuler\"\n" +
            "    }\n" +
            "}";

}
