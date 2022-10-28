package com.example.comp90018.ui;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.regions.servicemetadata.RekognitionServiceMetadata;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.AgeRange;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;




public class AwsTest {

    public static void main(String[] args) throws Exception {

        AwsTest a = new AwsTest();
        a.compareFaces();

//        String photo = "input.jpg";
//        String bucket = "bucket";
//        ProfileCredentialsProvider p = new ProfileCredentialsProvider();
//
//        String accessKey = "AKIA6IGQFLCKW76TNIGC";
//        String secretKey = "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";
//        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider(
//                new BasicAWSCredentials(accessKey, secretKey));
//        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard().withCredentials(credProvider)
//                .withRegion(Regions.US_WEST_2).build();
//
//
//        DetectFacesRequest request = new DetectFacesRequest()
//                .withImage(new Image()
//                        .withS3Object(new S3Object()
//                                .withName(photo)
//                                .withBucket(bucket)))
//                .withAttributes();
//        // Replace Attribute.ALL with Attribute.DEFAULT to get default values.
//
//        try {
//            DetectFacesResult result = rekognitionClient.detectFaces(request);
//            List < FaceDetail > faceDetails = result.getFaceDetails();
//
//            for (FaceDetail face: faceDetails) {
//                if (request.getAttributes().contains("ALL")) {
//                    AgeRange ageRange = face.getAgeRange();
//                    System.out.println("The detected face is estimated to be between "
//                            + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
//                            + " years old.");
//                    System.out.println("Here's the complete set of attributes:");
//                } else { // non-default attributes have null values.
//                    System.out.println("Here's the default set of attributes:");
//                }
//
//                ObjectMapper objectMapper = new ObjectMapper();
//                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
//            }
//
//        } catch (AmazonRekognitionException e) {
//            e.printStackTrace();
//        }

    }

    public void compareFaces() {
        String accessKey = "AKIA6IGQFLCKW76TNIGC";
        String secretKey = "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";
        Float similarityThreshold = 70F;
        String sourceImage = "source.jpg";
        String targetImage = "target.jpg";
        ByteBuffer sourceImageBytes = null;
        ByteBuffer targetImageBytes = null;
        ClientConfiguration config = new ClientConfiguration();
        config.setProtocol(Protocol.HTTPS);

        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(
                new BasicAWSCredentials(accessKey, secretKey),config);


        //Load source and target images and create input parameters
        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load source image " + sourceImage);
            System.exit(1);
        }
        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }

        Image source = new Image()
                .withBytes(sourceImageBytes);
        Image target = new Image()
                .withBytes(targetImageBytes);

        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(source)
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult = rekognitionClient.compareFaces(request);


        // Display results
        List <CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match: faceDetails){
            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + face.getConfidence().toString()
                    + "% confidence.");

        }
        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();

        System.out.println("There was " + uncompared.size()
                + " face(s) that did not match");
        System.out.println("Source image rotation: " + compareFacesResult.getSourceImageOrientationCorrection());
        System.out.println("target image rotation: " + compareFacesResult.getTargetImageOrientationCorrection());
    }
}

