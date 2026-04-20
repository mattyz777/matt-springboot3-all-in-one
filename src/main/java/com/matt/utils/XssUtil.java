package com.matt.utils;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class XssUtil {
    /**
     * XSS cases are handled as below based on owasp-java-html-sanitizer
     *
     * general rules for html sanitization
     *  - <script>xxx</script>                               ->  element is removed
     *  - <img src="x" onerror="alert(1)">xxx</img>          ->  element is removed
     *  - <a href="javascript:alert(1)">xxx</a>              ->  element is removed
     *  - <ifrom>xxx</iframe>                                ->  element is removed
     *
     *  - <div><script>xxx</script></div> =>  <div></div>    ->  only safe element is kept
     *  - <div onxxx>xxx</div>            =>  <div>xxx</div> ->  only safe element is kept
     *  - non html tags                   =>  < > & " '      ->  will be escaped to ASCII code
     *  - <br /><hr />                    =>  <br />         ->  none html tag is removed
     *
     *
     * general rules for text sanitization
     *  - <div style="...">xxx</div>      =>  xxx            ->  style is removed
     *  - <div><p>xx</p></div>            =>  xx             ->  nested tag is removed
     *  - non html tags                   =>  < > & " '      ->  will be escaped to ASCII code
     *
     *
     * 1. <script>alert(1)</script>
     *    -> removed (element not allowed)
     *
     * 2. <img src="x" onerror="alert(1)">
     *    -> <img> not allowed -> removed entirely
     *
     * 3. <a href="javascript:alert(1)">
     *    -> href removed (invalid protocol)
     *
     * 4. <a href="java&#x73;cript:alert(1)">
     *    -> normalized to javascript -> removed
     *
     * 5. <svg onload="alert(1)">
     *    -> svg not allowed -> removed
     *
     * 6. <svg><a xlink:href="javascript:alert(1)">
     *    -> svg removed entirely
     *
     * 7. <a href="data:text/html,...">
     *    -> data protocol not allowed -> removed
     *
     * 8. <div style="background:url(javascript:alert(1))">
     *    -> style attribute not allowed -> removed
     *
     * 9. <iframe src="https://evil.com">
     *    -> iframe not allowed -> removed
     *
     * 10. <form action="https://evil.com">
     *    -> form not allowed -> removed
     *
     * 11. <a href="http://evil.com">Click</a>
     *     -> SEO link Spam <a href="http://evil.com" rel="nofollow">Click</a>
     */
    private static final PolicyFactory HTML_POLICY =
            Sanitizers.FORMATTING
                    .and(Sanitizers.LINKS)
                    .and(Sanitizers.BLOCKS);

    /**
     * Plain text by removing ALL HTML tags and attributes.
     */
    private static final PolicyFactory TEXT_POLICY =
            new HtmlPolicyBuilder().toFactory();

    public static String cleanHtml(String input) {
        return input == null ? null : HTML_POLICY.sanitize(input);
    }

    public static String cleanText(String input) {
        return input == null ? null : TEXT_POLICY.sanitize(input);
    }
}