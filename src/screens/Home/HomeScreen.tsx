import React from 'react';
import {View, Text, Image, TouchableOpacity} from "react-native";
import Button from '../../components/MainButton';
import styles from './Header.styles';
import {Feather} from "@expo/vector-icons";
import MiddleSection from "./MiddleSection";

function handleTransferPress() {
    console.log('Transfer pressed');
}


function handleRechargePress() {
    console.log('Recharge pressed');
}

function handleLoanPress() {
    console.log('Loan pressed');
}

function handleInsurancePress() {
    console.log('Insurance pressed');
}

export default function App() {
    return (
        <View style={styles.main}>
        <View style={styles.container}>
            <View style={styles.leftSection}>
                <View style={styles.avatarContainer}>
                    <Image
                        source={require('../../../assets/phonepe-icon.png')} // Replace with your actual avatar image
                        style={styles.avatar}
                    />
                    <View style={styles.indianFlag} />
                </View>

                <View style={styles.addressSection}>
                    <View style={styles.addAddressRow}>
                        <Text style={styles.addAddressText}>Add Address</Text>
                        <Feather name="chevron-down" size={20} color="white" />
                    </View>
                    <Text style={styles.locationText}>Hanuman Nagar</Text>
                </View>
            </View>

            <View style={styles.rightSection}>
                <View style={styles.notificationContainer}>
                    <Feather name="bell" size={24} color="white" />
                    <View style={styles.badge}>
                        <Text style={styles.badgeText}>1</Text>
                    </View>
                </View>
                <TouchableOpacity>
                    <Feather name="help-circle" size={24} color="white" />
                </TouchableOpacity>
            </View>
        </View>
            <MiddleSection
                onTransferPress={handleTransferPress}
                onRechargePress={handleRechargePress}
                onLoanPress={handleLoanPress}
                onInsurancePress={handleInsurancePress}
            />
        </View>
    );
  }