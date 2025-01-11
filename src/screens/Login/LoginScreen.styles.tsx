import colors from "../../styles/colors";
import {Platform, StatusBar, StyleSheet} from "react-native";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        marginTop:Platform.OS === "android" ? StatusBar.currentHeight : 0,
        backgroundColor: colors.secondary,

    },
    section: {
        display: "flex",
        width:"100%",
        flexDirection:"column" ,
        alignItems: "center",
        marginTop: 50,
        padding: 20,
    },
    headerText: {
        alignSelf: "flex-start",
        fontSize: 24,
        fontWeight: "bold",
        color: colors.white,
        marginVertical: 10,
    },
    subHeaderText: {
        alignSelf: "flex-start",
        fontSize: 16,
        color: colors.gray,
        marginBottom: 20,
    },
    numberSection: {
        width: "100%",

    },
    number: {
        color: colors.white,
        fontSize: 20,
        fontWeight: "bold",
        marginBottom: 10,
    },
    input: {
        backgroundColor: colors.secondary,
        padding: 10,
        fontSize: 20,
        color: colors.white,
        borderRadius: 5,
        borderWidth: 2,
        height:"auto",
        borderColor:colors.primary,
        width: "100%",
        marginBottom: 20,
    },
    logo: {
        alignSelf:"flex-start",
        width: 50,
        height: 50,
    },
});

export default styles;