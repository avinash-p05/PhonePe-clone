import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Ionicons } from '@expo/vector-icons';
import { TouchableOpacity, View, Dimensions, GestureResponderEvent } from 'react-native';
import Home from '../screens/Home/HomeScreen';
import History from '../screens/History/HistoryScreen';
import Scanner from '../screens/Scanner/ScannerScreen';
import colors from "../styles/colors";

const Tab = createBottomTabNavigator();
const { width } = Dimensions.get('window');

interface CustomTabBarButtonProps {
    children: React.ReactNode;
    onPress?: (event: GestureResponderEvent) => void;
}

const CustomTabBarButton: React.FC<CustomTabBarButtonProps> = ({ children, onPress }) => (
    <TouchableOpacity
        style={{
            display: 'flex',
            alignSelf: 'center',
            justifyContent: 'center',
            alignItems: 'center',
            paddingBottom: 70,
            width: 70,
            height: 70,
        }}
        onPress={onPress}
    >
        <View
            style={{
                width: 70,
                height: 70,
                borderRadius: 35,
                backgroundColor: colors.primary,
                justifyContent: 'center',
                alignItems: 'center',
            }}
        >
            {children}
        </View>
    </TouchableOpacity>
);

const BottomTabNavigator: React.FC = () => {
    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
                tabBarIcon: ({ focused, color, size }) => {
                    let iconName: keyof typeof Ionicons.glyphMap = "home";
                    if (route.name === 'Home') {
                        iconName = focused ? "home" : "home-outline";
                    } else if (route.name === 'History') {
                        iconName = focused ? "arrow-forward-outline" : "arrow-forward";
                    }
                    return <Ionicons name={iconName} size={size} color={color} />;
                },
                tabBarStyle: {
                    height: 70,
                    paddingBottom: 12,
                    elevation:20,
                    shadowColor: colors.black,
                    backgroundColor: colors.secondary,
                },
                tabBarActiveTintColor: colors.white,
                tabBarInactiveTintColor: colors.gray,
                tabBarLabelStyle: {
                    fontSize: 12,
                    fontWeight: "bold",
                },
            })}
        >
            <Tab.Screen
                name="Home"
                component={Home}
                options={{
                    headerShown: false,
                }}
            />
            <Tab.Screen
                name="Scan"
                component={Scanner}
                options={{
                    headerShown: false,
                    tabBarIcon: () => <Ionicons name="qr-code-outline" color="#ffffff" size={30} />,
                    tabBarButton: (props) => <CustomTabBarButton {...props} />,
                    tabBarLabel: () => null,
                }}
            />
            <Tab.Screen
                name="History"
                component={History}
                options={{
                    headerShown: false,
                }}
            />
        </Tab.Navigator>
    );
};

export default BottomTabNavigator;
