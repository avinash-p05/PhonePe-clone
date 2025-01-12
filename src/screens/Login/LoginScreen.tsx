import React, { useState } from 'react';
import {
    View,
    Text,
    TouchableOpacity,
    SafeAreaView,
    StatusBar,
    StyleSheet,
    Platform,
    Image, TextInput,
} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import styles from "./LoginScreen.styles";
import MainButton from "../../components/MainButton";

// Define the navigation stack param list
type RootStackParamList = {
    Login: undefined;
    OTPVerification: { phoneNumber: string };
};

type NavigationProp = NativeStackNavigationProp<RootStackParamList, 'Login'>;

interface LoginScreenProps {
    onProceed: (phoneNumber: string) => any;
}

const LoginMain: React.FC<LoginScreenProps> = ({ onProceed }) => {
    const [phoneNumber, setPhoneNumber] = useState<string>('');
    const navigation = useNavigation<NavigationProp>();

    const handleProceed = () => {
        if (phoneNumber.length === 10) {
            onProceed(phoneNumber);
            // Navigate to OTP verification screen with phone number
            navigation.navigate('OTPVerification', { phoneNumber });
        }
    };

    return (
        <SafeAreaView style={styles.container}>
            <StatusBar barStyle="light-content" backgroundColor="#2C2840" />

            <View style={styles.header}>
                <TouchableOpacity style={styles.helpButton}>
                    <Text style={styles.helpButtonText}>?</Text>
                </TouchableOpacity>
            </View>

            {/* Logo */}
            <View style={styles.logoContainer}>
                    <Image style={styles.logo} source={require('../../../assets/phonepe-icon.png')}/>
            </View>

            {/* Title and Subtitle */}
            <Text style={styles.title}>Log in to PhonePe</Text>
            <Text style={styles.subtitle}>
                We will create an account if you don't have one.
            </Text>
            <Text style={styles.title2}>Enter mobile number</Text>

            {/* Phone Input Container */}
            <View style={styles.inputContainer}>
                <View style={styles.countryCode}>
                    <Image
                        source={require('../../../assets/img.png')}
                        style={styles.flag}
                    />
                    <Text style={styles.countryCodeText}>+91</Text>
                </View>
                <TextInput
                    style={styles.input}
                    keyboardType="numeric"
                    maxLength={10}
                    value={phoneNumber}
                    onChangeText={setPhoneNumber}
                    placeholder="Enter mobile number"
                    placeholderTextColor="#9995AD"
                />
            </View>

            <MainButton
                title={"Proceed"} onPress={handleProceed} disabled={phoneNumber.length !== 10}  />

            {/* Terms and Conditions */}
            <View style={styles.termsContainer}>
                <Text style={styles.termsText}>
                    By proceeding, you are agreeing to PhonePe's{' '}
                    <Text style={styles.termsLink}>Terms and Conditions</Text> &{' '}
                    <Text style={styles.termsLink}>Privacy Policy</Text>.
                </Text>
            </View>
        </SafeAreaView>
    );
};

export default LoginMain;