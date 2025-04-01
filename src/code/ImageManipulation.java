package code;

import image.APImage;
import image.Pixel;

// Copilot is OP.

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        String path = "/Users/oliverc/Desktop/11CS S2/Year-11-CS-Unit-9-2D-Arrays-Lab-Image-Manipulation-master/cyberpunk2077.jpg";
        APImage image = new APImage(path);
        image.draw();
        grayScale(path);
        blackAndWhite(path);
        edgeDetection(path, 20);
        reflectImage(path);
        rotateImage(path);
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a color image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel p : image) { // This script was actually provided in the APImage class already soooooooo what can I do
            int average = getAverageColor(p);
            p.setRed(average);
            p.setGreen(average);
            p.setBlue(average);
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColor(Pixel p) {
        int average = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
        return average;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a color image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel p : image) {
            int average = getAverageColor(p);
            if (average < 128) {
                p.setRed(0);
                p.setGreen(0);
                p.setBlue(0);
            } else {
                p.setRed(255);
                p.setGreen(255);
                p.setBlue(255);
            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average color value of the current pixel
     * 2. The average color value of the pixel to the left of the current pixel
     * 3. The average color value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we color the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()]; // Original pixels of the image
        int[][][] pixles = new int[image.getHeight()][image.getWidth()][3]; // New pixels of the image (as a 3D array): Is this really necessary?!
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                pixels[row][col] = image.getPixel(col, row);
                pixles[row][col][0] = image.getPixel(col, row).getRed();
                pixles[row][col][1] = image.getPixel(col, row).getGreen();
                pixles[row][col][2] = image.getPixel(col, row).getBlue();
            }
        }
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                int average = getAverageColor(pixels[row][col]);
                int leftAverage = 0;
                int belowAverage = 0;

                if (col > 0) {
                    leftAverage = getAverageColor(pixels[row][col - 1]);
                }
                if (row < image.getHeight() - 1) {
                    belowAverage = getAverageColor(pixels[row + 1][col]);
                }

                if (Math.abs(average - leftAverage) > threshold != Math.abs(average - belowAverage) > threshold) {
                    pixles[row][col][0] = 0;
                    pixles[row][col][1] = 0;
                    pixles[row][col][2] = 0;
                } else {
                    pixles[row][col][0] = 255;
                    pixles[row][col][1] = 255;
                    pixles[row][col][2] = 255;
                }
            }
        }
        updateImage(image, pixles);
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()]; // Original pixels of the image
        int[][][] pixles = new int[image.getHeight()][image.getWidth()][3]; // New pixels of the image (as a 3D array): Is this really necessary?!
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                pixels[row][col] = image.getPixel(col, row);
                pixles[row][col][0] = image.getPixel(col, row).getRed();
                pixles[row][col][1] = image.getPixel(col, row).getGreen();
                pixles[row][col][2] = image.getPixel(col, row).getBlue();
            }
        }
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                int reflectedCol = image.getWidth() - col - 1;
                pixles[row][col][0] = pixels[row][reflectedCol].getRed();
                pixles[row][col][1] = pixels[row][reflectedCol].getGreen();
                pixles[row][col][2] = pixels[row][reflectedCol].getBlue();
            }
        }
        updateImage(image, pixles);
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) { // CLOCKWISE!!!!! WHY DID IT HAVE TO BE CLOCKWISE!!!!!!! WHYYYYYY!!!!!!!!!!!!!
        APImage image = new APImage(pathToFile);
        APImage rotatedImage = new APImage(image.getHeight(), image.getWidth());
        Pixel[][] pixels = new Pixel[image.getWidth()][image.getHeight()]; // Original pixels of the image
        int[][][] pixles = new int[image.getWidth()][image.getHeight()][3]; // New pixels of the image (as a 3D array): Is this really necessary?!
        for (int col = 0; col < image.getWidth(); col++) {
            for (int row = 0; row < image.getHeight(); row++) {
                pixels[col][row] = image.getPixel(col, row);
                pixles[col][image.getHeight()-1-row][0] = image.getPixel(col, row).getRed();
                pixles[col][image.getHeight()-1-row][1] = image.getPixel(col, row).getGreen();
                pixles[col][image.getHeight()-1-row][2] = image.getPixel(col, row).getBlue();
            }
        }
        updateImage(rotatedImage, pixles);
        rotatedImage.draw();
    }

    // A helper method that updates the image with the new pixel values after modification.
    public static void updateImage(APImage image, int[][][] pixles) {
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                image.setPixel(col, row, new Pixel(pixles[row][col][0], pixles[row][col][1], pixles[row][col][2]));
            }
        }
    }

}
