import React, { useState, useEffect } from "react";
import { Text, View, StyleSheet, Button, Dimensions, Animated } from "react-native";
import { CameraView, Camera } from "expo-camera";
import styles from "./Scanner.styles";
import ScannerStyles from "./Scanner.styles";

const { width } = Dimensions.get('window');
const scannerWidth = width * 0.7; // Scanner width will be 70% of screen width
const scannerHeight = scannerWidth; // Making it square

interface ScannerProps {type: string; data: string;}

export default function Index() {
    const [hasPermission, setHasPermission] = useState(null);
    const [scanned, setScanned] = useState(false);
    const scanLineAnimation = new Animated.Value(0);

    useEffect(() => {
        const getCameraPermissions = async () => {
            const { status } = await Camera.requestCameraPermissionsAsync();
            // @ts-ignore
            setHasPermission(status === "granted");
        };

        getCameraPermissions();
    }, []);

    useEffect(() => {
        if (!scanned) {
            animateScanLine();
        }
    }, [scanned]);

    const animateScanLine = () => {
        scanLineAnimation.setValue(0);
        Animated.loop(
            Animated.sequence([
                Animated.timing(scanLineAnimation, {
                    toValue: scannerHeight,
                    duration: 2000,
                    useNativeDriver: true,
                }),
            ])
        ).start();
    };

    const handleBarcodeScanned = () => {
        setScanned(true);
        alert(`Scanned`);
    }
    if (hasPermission === null) {
        return <Text style={styles.permissionText}>Requesting camera permission...</Text>;
    }
    if (hasPermission === false) {
        return <Text style={styles.permissionText}>No access to camera</Text>;
    }

    return (
        <View style={styles.container}>
            <CameraView
                onBarcodeScanned={handleBarcodeScanned}
                barcodeScannerSettings={{
                    barcodeTypes: ["qr", "pdf417"],
                }}
                style={StyleSheet.absoluteFillObject}
            />

            {/* Overlay with transparent scanner window */}
            <View style={styles.overlay}>
                <View style={styles.scannerContainer}>
                    {/* Scanner corners */}
                    <View style={[styles.corner, styles.topLeft]} />
                    <View style={[styles.corner, styles.topRight]} />
                    <View style={[styles.corner, styles.bottomLeft]} />
                    <View style={[styles.corner, styles.bottomRight]} />

                </View>

                {/* Scan instruction text */}
                <Text style={styles.instructionText}>
                    Align QR code within the frame
                </Text>
            </View>
        </View>
    );
}
