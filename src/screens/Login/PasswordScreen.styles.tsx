import {StyleSheet} from "react-native";
import colors from "../../styles/colors";

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: colors.background,
        padding: 20,
    },
    header: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        marginBottom: 20,
    },
    backButton: {
        padding: 8,
    },
    backButtonText: {
        color: 'white',
        fontSize: 24,
    },
    headerTitle: {
        color: 'white',
        fontSize: 18,
        fontWeight: '600',
    },
    helpButton: {
        width: 30,
        height: 30,
        borderRadius: 15,
        borderWidth: 1,
        borderColor: colors.gray,
        alignItems: 'center',
        justifyContent: 'center',
    },
    helpButtonText: {
        color: 'white',
        fontSize: 18,
    },
    logoContainer: {
        alignItems: 'center',
        marginVertical: 20,
    },
    logo: {
        alignSelf: 'flex-start',
        width: 60,
        height: 60,
        borderRadius: 30,
        backgroundColor: colors.primary,
        justifyContent: 'center',
        alignItems: 'center',
    },
    logoText: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'bold',
    },
    title: {
        fontSize: 24,
        color: 'white',
        fontWeight: 'bold',
        marginBottom: 8,
    },
    subtitle: {
        fontSize: 16,
        color: '#9995AD',
        marginBottom: 30,
    },
    inputContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: colors.primary,
        borderRadius: 8,
        marginBottom: 20,
    },
    input: {
        flex: 1,
        color: 'white',
        fontSize: 18,
        padding: 12,
        backgroundColor: colors.secondary2,
    },
    eyeIcon: {
        padding: 12,
        backgroundColor: colors.secondary2,
    },
    dotsContainer: {
        flexDirection: 'row',
        justifyContent: 'center',
        marginBottom: 30,
    },
    dot: {
        width: 10,
        height: 10,
        borderRadius: 5,
        backgroundColor: colors.secondary,
        marginHorizontal: 8,
    },
    dotFilled: {
        backgroundColor: colors.primary
    },
    forgotContainer: {
        marginTop: 'auto',
        alignItems: 'center',
    },
    forgotText: {
        color: colors.primary,
        fontSize: 14,
    },
});

export default styles;