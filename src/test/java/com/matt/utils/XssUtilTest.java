package com.matt.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("XSS 工具类单元测试")
class XssUtilTest {

    // ==================== cleanHtml 测试 ====================

    @DisplayName("cleanHtml - 注释案例1: <script> 标签应被移除")
    @Test
    void testCleanHtml_ScriptTagRemoved() {
        String input = "<script>alert(1)</script>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("", result.trim());
    }

    @DisplayName("cleanHtml - 注释案例2: <img> 标签应被完全移除")
    @Test
    void testCleanHtml_ImgTagRemoved() {
        String input1 = "<img src=\"x\" onerror=\"alert(1)\">";
        String input2 = "<img src=\"x\" onmouseover=\"alert(1)\">";
        String input3 = "<img src=\"x\" onclick=\"alert(1)\">";
        String result1 = XssUtil.cleanHtml(input1);
        String result2 = XssUtil.cleanHtml(input2);
        String result3 = XssUtil.cleanHtml(input3);

        assertEquals("", result1);
        assertEquals("", result2);
        assertEquals("", result3);
    }

    @DisplayName("cleanHtml - 注释案例3: javascript: 协议的链接应移除 href")
    @Test
    void testCleanHtml_JavascriptProtocolRemoved() {
        String input = "<a href=\"javascript:alert(1)\">Hello</a>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("Hello", result);
    }

    @DisplayName("cleanHtml - 注释案例4: Unicode 混淆的 javascript: 应被识别并移除")
    @Test
    void testCleanHtml_UnicodeJavascriptRemoved() {
        String input = "<a href=\"java&#x73;cript:alert(1)\">Hello</a>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("Hello", result);
    }

    @DisplayName("cleanHtml - 注释案例5: <svg> 标签应被移除")
    @Test
    void testCleanHtml_SvgTagRemoved() {
        String input = "<svg onload=\"alert(1)\"><circle cx=\"50\" cy=\"50\" r=\"40\"/></svg>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("", result.trim());
    }

    @DisplayName("cleanHtml - 注释案例6: <svg> 嵌套 <a> 标签应被完全移除")
    @Test
    void testCleanHtml_SvgWithLinkRemoved() {
        String input = "<svg><a xlink:href=\"javascript:alert(1)\">Hello</a></svg>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("Hello", result);
    }

    @DisplayName("cleanHtml - 注释案例7: data: 协议的链接应移除 href")
    @Test
    void testCleanHtml_DataProtocolRemoved() {
        String input = "<a href=\"data:text/html,<script>alert(1)</script>\">Hello</a>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("Hello", result);
    }

    @DisplayName("cleanHtml - 注释案例8: style 属性应被移除")
    @Test
    void testCleanHtml_StyleAttributeRemoved() {
        String input = "<div style=\"background:url(javascript:alert(1))\">Content</div>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<div>Content</div>", result);
    }

    @DisplayName("cleanHtml - 注释案例9: <iframe> 标签应被移除")
    @Test
    void testCleanHtml_IframeTagRemoved() {
        String input = "<iframe src=\"https://evil.com\"></iframe>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("", result);
    }

    @DisplayName("cleanHtml - 注释案例10: <form> 标签应被移除")
    @Test
    void testCleanHtml_FormTagRemoved() {
        String input = "<form action=\"https://evil.com\"><input type=\"text\"/></form>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("", result.trim());
    }

    @DisplayName("cleanHtml - 防止 SEO 垃圾链接攻击 Link Spam")
    @Test
    void testCleanHtml_SafeHttpsLinkPreserved() {
        String input = "<a href=\"https://a.com\">Click</a>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<a href=\"https://a.com\" rel=\"nofollow\">Click</a>", result);
    }

    @DisplayName("cleanHtml - 防止 SEO 垃圾链接攻击 Link Spam")
    @Test
    void testCleanHtml_MailtoLinkPreserved() {
        String input = "<a href=\"mailto:a@example.com\">Email</a>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<a href=\"mailto:a&#64;example.com\" rel=\"nofollow\">Email</a>", result);
    }

    @DisplayName("cleanHtml - 格式化标签应保留")
    @Test
    void testCleanHtml_FormattingTagsPreserved() {
        String input = "<b>Bold</b> <i>Italic</i> <u>Underline</u> <strong>Strong</strong> <em>Emphasis</em>";
        String result = XssUtil.cleanHtml(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanHtml - 块级元素应保留")
    @Test
    void testCleanHtml_BlockElementsPreserved() {
        String input = "<h1>Title</h1><p>Paragraph</p><div>Div content</div><ul><li>Item</li></ul>";
        String result = XssUtil.cleanHtml(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanHtml - 混合安全和危险内容")
    @Test
    void testCleanHtml_MixedSafeAndDangerous() {
        String input = "<b>Safe</b><script>alert(1)</script><i>Also safe</i><iframe src='evil'></iframe>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<b>Safe</b><i>Also safe</i>", result);
    }

    @DisplayName("cleanHtml - 移除事件处理器 onxxx")
    @Test
    void testCleanHtml_EventHandlersRemoved() {
        String input = "<div onclick=\"alert(1)\" onmouseover=\"hack()\" onerror=\"evil()\">Content</div>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<div>Content</div>", result);
    }

    @DisplayName("cleanHtml - 空值应返回 null")
    @Test
    void testCleanHtml_NullInput() {
        assertNull(XssUtil.cleanHtml(null));
    }

    @DisplayName("cleanHtml - 空字符串应返回空字符串")
    @Test
    void testCleanHtml_EmptyString() {
        assertEquals("", XssUtil.cleanHtml(""));
    }

    @DisplayName("cleanHtml - 纯文本应保持不变")
    @Test
    void testCleanHtml_PlainText() {
        String input = "Hello World! This is plain text.";
        String result = XssUtil.cleanHtml(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanHtml - 嵌套的危险标签应全部移除")
    @Test
    void testCleanHtml_NestedDangerousTags() {
        String input = "<div><script><iframe></iframe></script></div>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<div></div>", result);
    }

    @DisplayName("cleanHtml - 大小写混淆的攻击应被阻止")
    @Test
    void testCleanHtml_CaseInsensitiveAttack() {
        String input = "<SCRIPT>alert(1)</SCRIPT><ScRiPt>alert(2)</ScRiPt>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("", result);
    }

    @DisplayName("cleanHtml - 转非 html tag(=)")
    @Test
    void testCleanHtml_SubSupSmallTags() {
        String input = "H<sub>2</sub>O E=mc<sup>2</sup> <small>Copyright</small>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("H<sub>2</sub>O E&#61;mc<sup>2</sup> <small>Copyright</small>", result);
    }

    @DisplayName("cleanHtml - 移除非html tag")
    @Test
    void testCleanHtml_BrHrTags() {
        String input = "Line 1<br/>Line 2<hr/>Line 3";
        String result = XssUtil.cleanHtml(input);

        assertEquals("Line 1<br />Line 2Line 3", result);
    }

    @DisplayName("cleanHtml - pre 非块级标签内容保留")
    @Test
    void testCleanHtml_BlockquotePreTags() {
        String input = "<blockquote>Quote</blockquote><pre>Code</pre>";
        String result = XssUtil.cleanHtml(input);

        assertEquals("<blockquote>Quote</blockquote>Code", result);
    }


    // ==================== cleanText 测试 ====================

    @DisplayName("cleanText - 移除所有 HTML 标签")
    @Test
    void testCleanText_RemoveAllTags() {
        String input = "<b>Bold</b> <i>Italic</i> <script>alert(1)</script>";
        String result = XssUtil.cleanText(input);

        assertEquals("Bold Italic ", result);
    }

    @DisplayName("cleanText - script 标签及内容应被移除")
    @Test
    void testCleanText_ScriptTagAndContentRemoved() {
        String input = "Hello<script>alert('XSS')</script>World";
        String result = XssUtil.cleanText(input);

        assertEquals("HelloWorld", result);
    }

    @DisplayName("cleanText - img 标签应被移除")
    @Test
    void testCleanText_ImgTagRemoved() {
        String input = "Text<img src='test.jpg'>More text";
        String result = XssUtil.cleanText(input);

        assertEquals("TextMore text", result);
    }

    @DisplayName("cleanText - 链接标签应被移除，保留文本")
    @Test
    void testCleanText_LinkTagRemoved() {
        String input = "<a href=\"https://example.com\">Link Text</a>";
        String result = XssUtil.cleanText(input);

        assertEquals("Link Text", result);
    }

    @DisplayName("cleanText - div 和其他块级元素应被移除")
    @Test
    void testCleanText_BlockElementsRemoved() {
        String input = "<div><p>Paragraph</p><h1>Title</h1></div>";
        String result = XssUtil.cleanText(input);

        assertEquals("ParagraphTitle", result);
    }

    @DisplayName("cleanText - iframe 和 form 应被移除")
    @Test
    void testCleanText_IframeAndFormRemoved() {
        String input = "<iframe src='evil'></iframe><form action='bad'></form>";
        String result = XssUtil.cleanText(input);

        assertEquals("", result);
    }

    @DisplayName("cleanText - svg 和事件处理器应被移除")
    @Test
    void testCleanText_SvgAndEventsRemoved() {
        String input = "<svg onload='alert(1)'><circle/></svg>";
        String result = XssUtil.cleanText(input);

        assertEquals("", result);
    }

    @DisplayName("cleanText - style 属性应被移除")
    @Test
    void testCleanText_StyleAttributeRemoved() {
        String input = "<div style='color:red'>Text</div>";
        String result = XssUtil.cleanText(input);

        assertEquals("Text", result);
    }

    @DisplayName("cleanText - 嵌套标签应全部移除")
    @Test
    void testCleanText_NestedTagsRemoved() {
        String input = "<div><b><i>Nested</i></b></div>";
        String result = XssUtil.cleanText(input);

        assertEquals("Nested", result);
    }

    @DisplayName("cleanText - 空值应返回 null")
    @Test
    void testCleanText_NullInput() {
        assertNull(XssUtil.cleanText(null));
    }

    @DisplayName("cleanText - 空字符串应返回空字符串")
    @Test
    void testCleanText_EmptyString() {
        assertEquals("", XssUtil.cleanText(""));
    }

    @DisplayName("cleanText - 纯文本应保持不变")
    @Test
    void testCleanText_PlainTextUnchanged() {
        String input = "This is plain text without any HTML.";
        String result = XssUtil.cleanText(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanText - 特殊字符应保留")
    @Test
    void testCleanText_SpecialCharactersPreserved() {
        String input = "Hello & World < > \" ' @ # $ % ^ & * ( )";
        String result = XssUtil.cleanText(input);

        assertEquals("Hello &amp; World &lt; &gt; &#34; &#39; &#64; # $ % ^ &amp; * ( )", result);
    }

    @DisplayName("cleanText - Unicode 字符应保留")
    @Test
    void testCleanText_UnicodePreserved() {
        String input = "你好世界 Привет мир";
        String result = XssUtil.cleanText(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanText - 换行符和空格应保留")
    @Test
    void testCleanText_WhitespacePreserved() {
        String input = "Line 1\nLine 2\tTabbed   Spaces";
        String result = XssUtil.cleanText(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanText - 数字和字母混合应保留")
    @Test
    void testCleanText_AlphanumericPreserved() {
        String input = "User123 Test456 ABC789";
        String result = XssUtil.cleanText(input);

        assertEquals(input, result);
    }

    @DisplayName("cleanText - XSS 攻击向量应被清除")
    @Test
    void testCleanText_XssVectorsCleared() {
        String[] xssVectors = {
                "<script>alert(1)</script>",
                "<img src=x onerror=alert(1)>",
                "<svg onload=alert(1)>",
                "<iframe src='evil.com'></iframe>",
                "<body onload=alert(1)>",
                "<input onfocus=alert(1) autofocus>",
                "<marquee onstart=alert(1)>",
                "<video><source onerror=alert(1)>"
        };

        for (String vector : xssVectors) {
            assertEquals("", XssUtil.cleanText(vector));
        }
    }

    @DisplayName("cleanText - HTML 解码后字符串被认为是正确")
    @Test
    void testCleanText_HtmlEntities() {
        String input = "&lt;script&gt;alert(1)&lt;/script&gt;";
        String result = XssUtil.cleanText(input);

        assertEquals("&lt;script&gt;alert(1)&lt;/script&gt;", XssUtil.cleanText(result));
    }

    @DisplayName("cleanText - 多个连续标签应全部移除")
    @Test
    void testCleanText_MultipleConsecutiveTags() {
        String input = "<b><i><u><s>Deeply nested</s></u></i></b>";
        String result = XssUtil.cleanText(input);

        assertEquals("Deeply nested", result.trim());
    }

    @DisplayName("cleanText - 标签内有特殊属性应被移除")
    @Test
    void testCleanText_TagsWithSpecialAttributes() {
        String input = "<div id='test' class='foo' data-value='123'>Content</div>";
        String result = XssUtil.cleanText(input);

        assertEquals("Content", result.trim());
    }

    @DisplayName("cleanText - 空白标签应被正确处理")
    @Test
    void testCleanText_EmptyTags() {
        String input = "<div></div><p></p><span></span>";
        String result = XssUtil.cleanText(input);

        assertEquals("", result.trim());
    }

    @DisplayName("cleanText - 自关闭标签应被移除")
    @Test
    void testCleanText_SelfClosingTags() {
        String input = "Text<br/><hr/><img src='test'/>More";
        String result = XssUtil.cleanText(input);

        assertEquals("TextMore", result.trim());
    }
}
