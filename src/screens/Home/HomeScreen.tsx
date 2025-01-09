import React from 'react';
import { View, Text, Image } from "react-native";
import Button from '../../components/MainButton';
import styles from './Home.styles';
export default function App() {
    return (
      <View style={styles.container}>
        <Text>This is Home Screen</Text>
        <Button title='Click Me'></Button>
      </View>
    );
  }