import React from "react";
import {Image, Text, TextInput, TouchableOpacity, View} from "react-native";
import styles from "./LoginScreen.styles"
import MainButton from "../../components/MainButton";

export default function LoginMain() {

    const [mobileNumber, setMobileNumber] = React.useState("");
    const [isProcessed, setIsProcessed] = React.useState(false);

    const handleProceed = () => {

    }

    return(
        <View style={styles.container}>
            <View style={styles.section}>
                <View style={styles.logo}>
                    <Image source={require('../../../assets/phonepe-icon.png')} style={styles.logo} />
                </View>
                <Text style={styles.headerText}>Log in to PhonePe</Text>
                <Text style={styles.subHeaderText}>We will create an account if you don't have one.</Text>
                <View style={styles.numberSection}>
                    <Text style={styles.number}>Enter mobile number</Text>
                    <TextInput style={styles.input} placeholder="Mobile number" keyboardType="number-pad" onChangeText={setMobileNumber} />
                </View>
                <MainButton title={"Proceed"} onPress={handleProceed} disabled={isProcessed} />
            </View>
        </View>
    )
}