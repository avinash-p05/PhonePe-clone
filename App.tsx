
import { Image, StyleSheet, Text, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import BottomTabNavigator from "./src/navigation/BottomNavigation";
import LoginMain from "./src/screens/Login/LoginScreen";

const Stack = createNativeStackNavigator();

export default function App() {
  return (
      <NavigationContainer>
          <Stack.Navigator initialRouteName={"Login"}>
              <Stack.Screen
                  name="Login"
                  component={LoginMain}
                  options={{ headerShown: false }}
              />
              {/*<Stack.Screen*/}
              {/*    name="Login"*/}
              {/*    component={LoginPage}*/}
              {/*    options={{ headerShown: false }}*/}
              {/*/>*/}
              {/*<Stack.Screen*/}
              {/*    name="Register"*/}
              {/*    component={RegisterPage}*/}
              {/*    options={{ headerShown: false }}*/}
              {/*/>*/}
              {/*<Stack.Screen*/}
              {/*    name="Developers"*/}
              {/*    component={Developers}*/}
              {/*    options={{ headerShown: false }}*/}
              {/*/>*/}
              <Stack.Screen
                  name="Main"
                  component={BottomTabNavigator}
                  options={{ headerShown: false }}
              />
          </Stack.Navigator>
      </NavigationContainer>
  );
}

const handleClick = () =>{
  alert("Button Clicked!");
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
