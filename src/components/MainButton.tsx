import React from "react";
import { TouchableOpacity, Text, StyleSheet, GestureResponderEvent, ViewStyle, TextStyle } from "react-native";
import colors from "../styles/colors";

type BtnProps = {
  title: string;
  onPress?: (event: GestureResponderEvent) => void; // Optional press handler
  style?: ViewStyle; // Optional custom styles for the button
  textStyle?: TextStyle; // Optional custom styles for the text
  disabled?: boolean; // Optional disabled state
};

const Button: React.FC<BtnProps> = ({ title, onPress, style, textStyle, disabled }) => {
  return (
    <TouchableOpacity
      onPress={onPress}
      style={[
        styles.button,
        style,
        disabled && styles.disabledButton, // Add disabled styles if button is disabled
      ]}
       // Prevent press feedback when disabled
      disabled={disabled}
    >
      <Text style={[styles.buttonText, textStyle]}>{title}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    marginTop:16,
    width:"100%",
    backgroundColor: colors.primary,
    padding: 16,
    borderRadius: 8,
    alignItems: "center",
  },
  buttonText: {
    color: "white",
    fontSize: 18,
    fontWeight: "bold",
  },
  disabledButton: {
    backgroundColor: colors.secondary2,
  },
});

export default Button;
