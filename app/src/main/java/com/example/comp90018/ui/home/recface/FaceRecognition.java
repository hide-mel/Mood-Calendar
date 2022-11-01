package com.example.comp90018.ui.home.recface;

import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProviderFactory;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.Emotion;
import software.amazon.awssdk.services.rekognition.model.RekognitionException;
import software.amazon.awssdk.services.rekognition.model.DetectFacesRequest;
import software.amazon.awssdk.services.rekognition.model.DetectFacesResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.Attribute;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;
import software.amazon.awssdk.services.rekognition.model.AgeRange;
import software.amazon.awssdk.core.SdkBytes;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaceRecognition {
    public Map<String,String> recognition(String uri) {

        if (uri == null){
            return null;
        }
        final String usage = "\n" +
                "Usage: " +
                "   <sourceImage>\n\n" +
                "Where:\n" +
                "   sourceImage - The path to the image (for example, C:\\AWS\\pic1.png). \n\n";
//
//        if (args.length != 1) {
//            System.out.println(usage);
//            System.exit(1);
//        }
//
        String sourceImage = uri;
        Log.d("TAG", "recognition: " + uri);
        Region region = Region.US_EAST_1;


        RekognitionClient rekClient = RekognitionClient.builder()
                .region(region)
                .credentialsProvider(() -> {
                    AwsCredentials s = null;
                    try {
                        Log.d("zisen", "recognition: in" );

                        Class<?> c = Class.forName("software.amazon.awssdk.auth.credentials.AwsBasicCredentials");
                        Constructor constructor = c.getDeclaredConstructor(String.class,String.class);
                        Object o = constructor.newInstance("AKIA6IGQFLCKW76TNIGC","YR33ZsCqgjdg6oI9+uZ66ZARisjqkF6oDDHs/3Bh");
                        s = (AwsBasicCredentials) o;
                        Log.d("TAG", "recognition: " + s.accessKeyId());

                    } catch (ClassNotFoundException e) {
                        Log.d("TAG", "recognition: " + e.getMessage());
                    } catch (NoSuchMethodException e) {
                        Log.d("TAG", "recognition: " + e.getMessage());
                    } catch (InvocationTargetException e) {
                        Log.d("TAG", "recognition: " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        Log.d("TAG", "recognition: " + e.getMessage());
                    } catch (InstantiationException e) {
                        Log.d("TAG", "recognition: " + e.getMessage());
                    }
                    return s;

                })

                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();


        System.out.println(rekClient);

        Map<String,String> res = detectFacesinImage(rekClient, sourceImage );
        rekClient.close();
        return res;
    }

    // snippet-start:[rekognition.java2.detect_faces.main]
    public Map<String, String> detectFacesinImage(RekognitionClient rekClient, String sourceImage ) {
        Map<String,String> res = new HashMap<>();
        try {
            InputStream sourceStream = new FileInputStream(sourceImage);
            SdkBytes sourceBytes = SdkBytes.fromInputStream(sourceStream);

            // Create an Image object for the source image.
            Image souImage = Image.builder()
                    .bytes(sourceBytes)
                    .build();

            DetectFacesRequest facesRequest = DetectFacesRequest.builder()
                    .attributes(Attribute.ALL)
                    .image(souImage)
                    .build();

            DetectFacesResponse facesResponse = rekClient.detectFaces(facesRequest);
            List<FaceDetail> faceDetails = facesResponse.faceDetails();
            if (faceDetails.size() > 1){
                res.put("emotion","More than one face detected");
                return res;
            }
            for (FaceDetail face : faceDetails) {
                AgeRange ageRange = face.ageRange();
                System.out.println("The detected face is estimated to be between "
                        + ageRange.low().toString() + " and " + ageRange.high().toString()
                        + " years old.");

                System.out.println("There is a smile : "+face.smile().value().toString());
                for (Emotion e :face.emotions()){
                    System.out.println(e.typeAsString() + " | Confidence: " + e.confidence());
                }
                Log.d("zisen", "detectFacesinImage: " + face.emotions().get(0).typeAsString());
                res.put("emotion",face.emotions().get(0).typeAsString());
                res.put("confidence",face.emotions().get(0).confidence().toString());
            }
            Log.d("TAG", "detectFacesinImage: end");
        } catch (RekognitionException | FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return res;
    }
}
