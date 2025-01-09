import React from 'react';
import { View, Text, Image } from "react-native";
import Button from '../../components/MainButton';
import styles from './History.styles';
export default function App() {
    return (
        <View style={styles.container}>
            <Text>This is Home Screen</Text>
            <Button title='Click Me'></Button>
            <Image source={require("../../../assets/phonepe-icon.png")} style={{resizeMode:"contain", width:"50%"}}></Image>
        </View>
    );
}