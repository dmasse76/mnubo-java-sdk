/*
 * ---------------------------------------------------------------------------
 * 
 * COPYRIGHT (c) 2015 Mnubo Inc. All Rights Reserved.
 * 
 * The copyright to the computer program(s) herein is the property of Mnubo Inc. The program(s) may be used and/or
 * copied only with the written permission from Mnubo Inc. or in accordance with the terms and conditions stipulated in
 * the agreement/contract under which the program(s) have been supplied.
 * 
 * Author: marias Date : Aug 11, 2015
 * 
 * ---------------------------------------------------------------------------
 */

package com.mnubo.java.sdk.client.utils;

import static com.mnubo.java.sdk.client.Constants.HTTP_PROTOCOL;
import static com.mnubo.java.sdk.client.Constants.MAX_PORT_VALUE;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class ValidationUtils
{
    public static double MIN_LATITUDE = -90;
    public static double MAX_LATITUDE = 90;
    public static double MIN_LONGITUDE = -180;
    public static double MAX_LONGITUDE = 180;

    public static void checkMandatoryProperty( String propertyValue, String property )
    {
        if ( isBlank( propertyValue ) )
        {
            throw new IllegalStateException( String.format( "%s property is mandatory." , property ) );
        }
    }

    public static int parseAsInteger( String propertyValue, String property )
    {
        parseAsString( propertyValue , property );
        if ( propertyValue.matches( "\\d+" ) )
        {
            try
            {
                int value = Integer.parseInt( propertyValue );
                return value;
            }
            catch ( Exception ex )
            {}
        }
        throw new IllegalStateException(
                                         String.format( "%s property has to be an Integer positive." , property ) );
    }

    public static boolean parseAsBoolean( String propertyValue, String property )
    {
        parseAsString( propertyValue , property );
        try
        {
            return Boolean.valueOf( propertyValue );
        }
        catch ( Exception ex )
        {
            throw new IllegalStateException( String.format( "%s property has to be a boolean valid." , property ) );
        }
    }

    public static String parseAsString( String propertyValue, String property )
    {
        if ( isBlank( propertyValue ) )
        {
            throw new IllegalStateException( String.format( "%s property is not a valid String." , property ) );
        }
        return propertyValue;
    }

    public static int parseAsPort( String propertyValue, String property )
    {
        int value = parseAsInteger( propertyValue , property );
        if ( value > MAX_PORT_VALUE )
        {
            throw new IllegalStateException( String.format( "%s property is not a valid port." , property ) );
        }
        return value;
    }

    public static String parseAsHttpProtocol( String httpProtocol )
    {
        parseAsString( httpProtocol , HTTP_PROTOCOL );
        if ( !httpProtocol.equalsIgnoreCase( "http" ) && !httpProtocol.equalsIgnoreCase( "https" ) )
        {
            throw new IllegalStateException( String.format( "%s has to be equal to \"http\" or \"https\"" ,
                                                            HTTP_PROTOCOL ) );
        }
        return httpProtocol;
    }

    public static boolean validNotNull( Object object, String objectName )
    {
        if ( object == null )
        {
            throw new IllegalStateException( String.format( "%s cannot be null." , objectName ) );
        }
        return true;
    }

    public static boolean validIsFile( File fileName )
    {
        validNotNull( fileName , "configuration file" );
        if ( !fileName.exists() || fileName.isDirectory() )
        {
            throw new IllegalStateException( String.format( "This file, %s does not exist or is a folder" ,
                                                            fileName.getAbsolutePath() ) );
        }
        return true;
    }

    public static boolean validIsBlank( String object, String message )
    {
        if ( isBlank( object ) )
        {
            throw new IllegalStateException( message );
        }
        return true;
    }

    public static boolean isBlank( String object )
    {
        return StringUtils.isBlank( object );
    }

    public static double isValidLatitude( String latitude )
    {
        if ( latitude.matches( "^-?\\d*(\\.\\d+)?$" ) )
        {
            try
            {
                double value = Double.parseDouble( latitude );
                if ( value >= MIN_LATITUDE && value <= MAX_LATITUDE )
                {
                    return value;
                }
            }
            catch ( Exception ex )
            {}
        }
        throw new IllegalStateException(
                                         String.format( "This latitude value %s, is not valid." , latitude ) );
    }

    public static double isValidLongitude( String longitude )
    {
        if ( longitude.matches( "^-?\\d*(\\.\\d+)?$" ) )
        {
            try
            {
                double value = Double.parseDouble( longitude );
                if ( value >= MIN_LATITUDE && value <= MAX_LATITUDE )
                {
                    return value;
                }
            }
            catch ( Exception ex )
            {}
        }
        throw new IllegalStateException(
                                         String.format( "This latitude value %s, is not valid." , longitude ) );
    }

    public static double isValidElevation( String elevation )
    {
        if ( elevation.matches( "^-?\\d*(\\.\\d+)?$" ) )
        {
            try
            {
                double value = Double.parseDouble( elevation );
                return value;
            }
            catch ( Exception ex )
            {}
        }
        throw new IllegalStateException(
                                         String.format( "This latitude value %s, is not valid." , elevation ) );
    }

}
