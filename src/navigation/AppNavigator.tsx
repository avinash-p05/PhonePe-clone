import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import LoginScreen from '../screens/Login/LoginScreen';
import OTPVerificationScreen from '../screens/Login/PasswordScreen';
import BottomTabNavigator from "./BottomNavigation";
import colors from "../styles/colors";

// Define the type for your navigation stack parameters
export type RootStackParamList = {
    Login: undefined;
    OTPVerification: { phoneNumber: string };
    Main: undefined;
    // Add other screens here as needed
};

const Stack = createNativeStackNavigator<RootStackParamList>();

export const AppNavigator = () => {
    return (
        <NavigationContainer>
            <Stack.Navigator
                initialRouteName="Login"
                screenOptions={{
                    headerShown: false,
                    contentStyle: { backgroundColor: colors.background },
                    animation: 'slide_from_right',
                }}
            >
                <Stack.Screen
                    name="Login"
                    component={(props: any) => <LoginScreen {...props} onProceed={() => {}} />}
                    options={{
                        headerShown: false,
                        gestureEnabled: false,
                    }}
                />
                <Stack.Screen
                    name="OTPVerification"
                    component={(props: any) => <OTPVerificationScreen {...props} onProceed={() => {}} />}
                    options={{
                        headerShown: false,
                        gestureEnabled: false,
                    }}
                />
                <Stack.Screen
                    name="Main"
                    component={BottomTabNavigator}
                    options={{ headerShown: false }}
                />
            </Stack.Navigator>
        </NavigationContainer>
    );
};