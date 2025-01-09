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
      activeOpacity={disabled ? 1 : 0.7} // Prevent press feedback when disabled
      disabled={disabled}
    >
      <Text style={[styles.buttonText, textStyle]}>{title}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    margin:20,
    width:"80%",
    backgroundColor: colors.primary,
    padding: 12,
    borderRadius: 8,
    alignItems: "center",
  },
  buttonText: {
    color: "white",
    fontSize: 16,
    fontWeight: "bold",
  },
  disabledButton: {
    backgroundColor: "#ccc",
  },
});

export default Button;
