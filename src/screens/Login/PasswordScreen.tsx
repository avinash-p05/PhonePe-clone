import React, { useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    SafeAreaView,
    StyleSheet,
    TextInput,
    StatusBar, Image,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import {Ionicons} from "@expo/vector-icons";
import colors from "../../styles/colors";
import MainButton from "../../components/MainButton";
import styles from "./PasswordScreen.styles";

type RootStackParamList = {
    Login: undefined;
    OTPVerification: { phoneNumber: string };
    Main: undefined;
};

type NavigationProp = NativeStackNavigationProp<RootStackParamList, 'OTPVerification'>;

const OTPVerificationScreen = () => {
    const [passcode, setPasscode] = useState<string>('');
    const [showPasscode, setShowPasscode] = useState<boolean>(false);
    const navigation = useNavigation<NavigationProp>();

    const handlePasscodeChange = (text: string) => {
        // Only allow numbers and limit to 4 digits
        const numericText = text.replace(/[^0-9]/g, '');
        if (numericText.length <= 4) {
            setPasscode(numericText);
        }
    };

    const handleProceed = () => {
        if (passcode.length === 4) {
            // Handle OTP verification logic here
            navigation.navigate('Main');
        }
    };

    const handleBack = () => {
        navigation.goBack();
    };

    return (
        <SafeAreaView style={styles.container}>

            {/* Header */}
            <View style={styles.header}>
                <TouchableOpacity onPress={handleBack} style={styles.backButton}>
                    <Text style={styles.backButtonText}>←</Text>
                </TouchableOpacity>
                <Text style={styles.headerTitle}>Continue with PhonePe</Text>
                <TouchableOpacity style={styles.helpButton}>
                    <Text style={styles.helpButtonText}>?</Text>
                </TouchableOpacity>
            </View>

            {/* Logo */}
            <View style={styles.logoContainer}>
                <Image style={styles.logo} source={require('../../../assets/phonepe-icon.png')}/>
            </View>

            {/* Title and Subtitle */}
            <Text style={styles.title}>Verify your mobile number</Text>
            <Text style={styles.subtitle}>
                This verifies your identity and helps you securely log into PhonePe
            </Text>

            {/* PhoneInput Container */}
            <View style={styles.inputContainer}>
                <TextInput
                    style={styles.input}
                    value={passcode}
                    onChangeText={handlePasscodeChange}
                    keyboardType="numeric"
                    maxLength={4}
                    secureTextEntry={!showPasscode}
                    placeholder="Enter 4-digit Passcode"
                    placeholderTextColor="#9995AD"
                />
                <TouchableOpacity
                    style={styles.eyeIcon}
                    onPress={() => setShowPasscode(!showPasscode)}
                >
                    <Ionicons name="eye" color="#9995AD" size={24} />
                </TouchableOpacity>
            </View>

            {/* Passcode Input */}

            {/* Passcode Dots */}
            <View style={styles.dotsContainer}>
                {[...Array(4)].map((_, index) => (
                    <View
                        key={index}
                        style={[
                            styles.dot,
                            index < passcode.length && styles.dotFilled
                        ]}
                    />
                ))}
            </View>

            {/* Proceed Button */}
            <MainButton title={"Proceed"} onPress={handleProceed} disabled={passcode.length !== 4} />

            {/* Forgot Passcode */}
            <TouchableOpacity style={styles.forgotContainer}>
                <Text style={styles.forgotText}>Forgot Passcode? Try another way</Text>
            </TouchableOpacity>
        </SafeAreaView>
    );
};


export default OTPVerificationScreen;