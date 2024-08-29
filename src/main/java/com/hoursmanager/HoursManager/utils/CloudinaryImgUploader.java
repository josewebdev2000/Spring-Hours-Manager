package com.hoursmanager.HoursManager.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

public class CloudinaryImgUploader
{
    private final Cloudinary cloudinary;

    public CloudinaryImgUploader(String cloudName, String apiKey, String apiSecret)
    {
        // Initialize Cloudinary
        cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", apiKey,
                        "api_secret", apiSecret
                )
        );
    }

    private String generateUniqueId()
    {
        return String.valueOf(System.currentTimeMillis());
    }

    public String replaceBase64WithUrls(String htmlSnippet) throws Exception
    {
        // Parse the HTML content
        Document doc = Jsoup.parse(htmlSnippet);

        // Iterate through all <img> tags
        for (Element img : doc.select("img")) {
            String src = img.attr("src");

            // Generate a unique ID for the image
            String id = generateUniqueId();

            // Check if the src is a base-64 encoded image
            if (src.startsWith("data:image")) {
                // Upload the image to Cloudinary and get the URL
                Map<String, Object> uploadResult = cloudinary.uploader().upload(src, ObjectUtils.asMap(
                        "public_id", id
                ));

                String imgUrl = (String) uploadResult.get("secure_url");

                // Print the new img Url
                System.out.println(imgUrl);

                // Replace the src attribute with the URL
                img.attr("src", imgUrl);
            }
        }

        // Return the modified HTML
        return doc.body().html();
    }

}
